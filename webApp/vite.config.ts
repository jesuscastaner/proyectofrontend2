import react from "@vitejs/plugin-react";
import { defineConfig } from "vite";

export default defineConfig({
    root: ".",
    plugins: [react()],
    build: {
        outDir: "dist",
        emptyOutDir: true,
    },
    server: { port: 8080 },
});
