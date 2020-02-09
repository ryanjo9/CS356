import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    books: [],
    username: null
  },
  mutations: {
    setBooks(state, books) {
      state.books = books;
    },
    addBook(state, book) {
      state.books.push(book);
    },
    setUsername(state, username) {
      state.username = username;
    }
  },
  actions: {
    /**
     * This takes an object with a username and password. 
     * It would be used to generate a token, but for this project,
     * just commits the username to state and returns the username
     * @param {*} context 
     * @param {*} data object with username and password
     */
    async login(context, data) {
      try {
        context.commit('setUsername', data.username);
        return data.username
      } catch (error) {
        return error.response.data.message;
      }
    },
    /**
     * This retrieves an array of all books stored in the database
     * @param {*} context 
     */
    async getBooks(context) {
      try {
        const { data } = await axios.get("/api/books");
        context.commit('setBooks', data);
        return ''; // does this really need to return a string?
      } catch (error) {
        console.error(error);
        return error;
      }
    },
    /**
     * This adds a book object that has already been validated and saves
     * it to the database
     * @param {*} context 
     * @param {*} data - the book object 
     */
    async addBook(context, data) {
      if (data) {
        try {
          const { data } = await axios.post('/api/save', data);
          // should this commit the book to the books array here?
        } catch (error) {
          console.error(error);
          return '';
        }
      } 
      
      return '';
    } 
  },
  modules: {
  }
})
