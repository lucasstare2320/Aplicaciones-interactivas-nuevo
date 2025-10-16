import { configureStore } from "@reduxjs/toolkit"
import postReducer from "./userSlide"

export const store = configureStore({
    reducer: {posts: postReducer}
})

