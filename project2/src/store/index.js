import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    books: [],
    categories: [],
    searchResult: [],
    username: null,
    bookData: null
  },
  mutations: {
    setBooks(state, books) {
      state.books = books;
    },
    setCategories(state, categories) {
      state.categories = categories;
    },
    addBook(state, book) {
      state.books.push(book);
    },
    setUsername(state, username) {
      state.username = username;
    },
    setSearchResult(state, result) {
      state.searchResult = result;
    },
    setBookData(state, book) {
      state.bookData = book;
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
        return ''; // returning an empty string since my old code relied on empty strings to handle errors
      } catch (error) {
        return error.response.data.message;
      }
    },
    /**
     * This retrieves an array of all books and categories object stored in the database
     * @param {*} context 
     */
    async getBooks(context) {
      try {
        console.log('getting books');
        const { data } = await axios.get('/api/books');

        context.commit('setBooks', data.books);
        context.commit('setCategories', data.categories);

        return ''; // returning an empty string since my old code relied on empty strings for error handling
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
          console.log('data: ', data);
          await axios.post('/api/save', data);
          // should this commit the book to the books array here?
          // probably not since the home page retrieves all books on creation
          // could make the home page check if there are already books in state
          return ''; // returning an empty string since my old code relied on empty string for errors
        } catch (error) {
          console.error(error);
          return error;
        }
      } 
      
      return '';
    },
    async search(context, isbn) {
      try {
        const { data } = await axios.get(
          `http://openlibrary.org/api/books?bibkeys=ISBN:${isbn}&format=json&jscmd=data`
        )

        const body = data[`ISBN:${isbn}`]

        if (body) {
          const book = {
            isbn,
            title: body.title || null,
            author: body.authors[0].name || null,
            category: body.subjects[0].name || null,
            imagePath: body.cover.medium || null
          }

          context.commit('setBookData', book);
          return book
        } else {
          context.commit('setBookData', {})
          return {}
        }
        
      } catch (error) {
        console.error(error);
        return error;
      }
    },
    clearBook(context) {
      context.commit('setBookData', null)
      return ''
    }
  },
  modules: {
  }
})
