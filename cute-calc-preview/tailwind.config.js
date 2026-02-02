/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'cute-pink': '#F9B3A9',
        'cute-pink-light': '#FFE0DB',
        'cute-pink-bright': '#FFF8F7',
        'cute-dark': '#410002',
      },
    },
  },
  plugins: [],
}
