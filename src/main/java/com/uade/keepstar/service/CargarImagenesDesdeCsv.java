package com.uade.keepstar.service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.sql.*;
import java.util.*;
import java.util.Base64;

public class CargarImagenesDesdeCsv {

    // Default URL lista para MySQL 8
    private static final String DEFAULT_URL =
        "jdbc:mysql://localhost:3306/keepstar?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DEFAULT_USER     = "root";
    private static final String DEFAULT_PASSWORD = "tu_password";

    private static final String CSV_PATH_DEFAULT =
        "C:/Users/juanm/Downloads/Apis/product_images_base64.csv";

    public static void main(String[] args) {
        String csvPath = args != null && args.length > 0 ? args[0] : CSV_PATH_DEFAULT;

        DbConfig cfg = loadDbConfig();
        cfg.url = ensureUrlFlags(cfg.url);

        System.out.println("CSV: " + csvPath);
        System.out.println("DB URL: " + cfg.url);

        try (Connection conn = DriverManager.getConnection(cfg.url, cfg.user, cfg.password)) {
            System.out.println("✅ Conectado a la base de datos");
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery("SELECT DATABASE()")) {
                if (rs.next()) System.out.println("🔎 DATABASE(): " + rs.getString(1));
            }

            boolean hasImageName = hasColumn(conn, "image_table", "image_name");
            System.out.println("image_table.image_name existe? " + hasImageName);
            if (!hasImageName) {
                System.err.println("❌ Falta la columna image_name en image_table.");
                return;
            }

            final String sqlExistsProduct = "SELECT 1 FROM product WHERE id = ?";
            final String sqlUpsert =
                "INSERT INTO image_table (image, product_id, image_name) " +
                "VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE image = VALUES(image)";

            int inserted = 0, updated = 0, skipped = 0, errors = 0, total = 0;

            try (BufferedReader br = Files.newBufferedReader(Paths.get(csvPath), StandardCharsets.UTF_8);
                 PreparedStatement psExists = conn.prepareStatement(sqlExistsProduct);
                 PreparedStatement psUpsert = conn.prepareStatement(sqlUpsert)) {

                String header = br.readLine();
                if (header == null) { System.err.println("❌ CSV vacío"); return; }

                String line;
                while ((line = br.readLine()) != null) {
                    total++;
                    String[] parts = splitCsvLine(line, 3);
                    if (parts.length < 3) { skipped++; continue; }

                    String pidStr  = parts[0].trim();
                    String file    = parts[1].trim();
                    String b64     = parts[2].trim();

                    if (pidStr.isEmpty() || b64.isEmpty()) { skipped++; continue; }

                    try {
                        long productId = Long.parseLong(pidStr);
                        byte[] bin = Base64.getDecoder().decode(b64);

                        // FK: existe product?
                        psExists.setLong(1, productId);
                        try (ResultSet rs = psExists.executeQuery()) {
                            if (!rs.next()) {
                                errors++;
                                System.err.println("⚠️  No existe product_id=" + productId + " (salto " + file + ")");
                                continue;
                            }
                        }

                        // UPSERT
                        psUpsert.setBytes(1, bin);
                        psUpsert.setLong(2, productId);
                        psUpsert.setString(3, file);
                        int affected = psUpsert.executeUpdate(); // 1=insert, 2=update
                        if (affected == 1) inserted++;
                        else if (affected == 2) updated++;
                        else skipped++;

                        if ((inserted + updated) % 25 == 0) {
                            System.out.println("✔️ Nuevas=" + inserted + " | Actualizadas=" + updated + " / Procesadas=" + total);
                        }
                    } catch (Exception ex) {
                        errors++;
                        System.err.println("❌ Error en línea " + total + " (" + file + "): " + ex.getMessage());
                    }
                }
            }

            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM image_table")) {
                rs.next();
                System.out.println("📦 image_table rows ahora: " + rs.getLong(1));
            }

            System.out.printf("🎉 Fin. Total=%d, Nuevas=%d, Actualizadas=%d, Skip=%d, Err=%d%n",
                    total, inserted, updated, skipped, errors);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ---------------- helpers ---------------- */

    private static DbConfig loadDbConfig() {
        Properties p = new Properties();

        // 1) classpath (VSCode/Spring)
        try (InputStream in = CargarImagenesDesdeCsv.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (in != null) {
                p.load(in);
                System.out.println("ℹ️  Usando credenciales de (classpath): application.properties");
            }
        } catch (IOException ignored) {}

        // 2) rutas conocidas (fallback)
        if (p.isEmpty()) {
            String[] candidates = {
                "src/main/resources/application.properties",
                "application.properties",
                "resources/application.properties",
                "Aplicaciones-interactivas-nuevo/src/main/resources/application.properties"
            };
            for (String path : candidates) {
                Path file = Paths.get(path);
                if (Files.exists(file)) {
                    try (InputStream in = Files.newInputStream(file)) {
                        p.load(in);
                        System.out.println("ℹ️  Usando credenciales de: " + file.toAbsolutePath());
                        break;
                    } catch (IOException ignored) {}
                }
            }
        }

        String url  = first(p, new String[]{"spring.datasource.url","datasource.url"}, DEFAULT_URL);
        String usr  = first(p, new String[]{"spring.datasource.username","datasource.username"}, DEFAULT_USER);
        String pwd  = first(p, new String[]{"spring.datasource.password","datasource.password"}, DEFAULT_PASSWORD);
        return new DbConfig(url, usr, pwd);
    }

    private static String ensureUrlFlags(String url) {
        if (url == null || url.isBlank()) return DEFAULT_URL;
        String lower = url.toLowerCase(Locale.ROOT);
        String out = url;

        if (!lower.contains("allowpublickeyretrieval")) {
            out += (out.contains("?") ? "&" : "?") + "allowPublicKeyRetrieval=true";
        }
        if (!lower.contains("usessl")) {
            out += (out.contains("?") ? "&" : "?") + "useSSL=false";
        }
        if (!lower.contains("servertimezone")) {
            out += (out.contains("?") ? "&" : "?") + "serverTimezone=UTC";
        }
        return out;
    }

    private static String first(Properties p, String[] keys, String def) {
        for (String k : keys) {
            String v = p.getProperty(k);
            if (v != null && !v.isBlank()) return v.trim();
        }
        return def;
    }

    private static boolean hasColumn(Connection conn, String table, String column) {
        try (ResultSet rs = conn.getMetaData().getColumns(conn.getCatalog(), null, table, column)) {
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

    private static String[] splitCsvLine(String line, int limit) {
        String[] parts = line.split(",", limit);
        for (int i = 0; i < parts.length; i++) parts[i] = unquote(parts[i].trim());
        return parts;
    }

    private static String unquote(String s) {
        return (s.length() >= 2 && s.startsWith("\"") && s.endsWith("\""))
                ? s.substring(1, s.length() - 1) : s;
    }

    private static class DbConfig {
        String url, user, password;
        DbConfig(String url, String user, String password) { this.url=url; this.user=user; this.password=password; }
    }
}
