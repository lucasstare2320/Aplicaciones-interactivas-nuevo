import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios"

const URL = "http://localhost:8080/auth/register"

export const postUsuario = createAsyncThunk("users/postUser", async(newuser) =>{
  console.log(newuser)
 const {data} = await axios.post(URL, newuser);
 console.log(data)
 return data
 
})


const userSlide = createSlice({
    name:"users",
    initialState:{
        user:{},
        loading: false,
        error: null
    },
    reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(postUsuario.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(postUsuario.fulfilled, (state, action) => {
        state.loading = false;
        state.items = action.payload;
      })
      .addCase(postUsuario.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message;
      })
    },
});
export default userSlide.reducer;
