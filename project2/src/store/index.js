import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import * as mdbvue from 'mdbvue'
for (const component in mdbvue) {
  Vue.component(component, mdbvue[component])
}

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    books: [],
    categories: [],
    searchResult: [],
    username: null,
    bookData: null,
    editBook: null
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
    },
    setEditBook(state, book) {
      state.editBook = book
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
        const { data } = await axios.get('/api/books');
        console.log('data: ', data)

        context.commit('setBooks', data.books);
        context.commit('setCategories', data.categories);

        return data.books; // returning an empty string since my old code relied on empty strings for error handling
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
            title: body.title ? body.title : null,
            author: body.authors ? body.authors[0].name : null,
            category: body.subjects ? body.subjects[0].name : null,
            imagePath: body.cover ? body.cover.medium : null
          }

          context.commit('setBookData', book);
          return book
        } else {
          context.commit('setBookData', { isbn })
          return {isbn}
        }
        
      } catch (error) {
        console.error(error);
        return error;
      }
    },
    clearBook(context) {
      context.commit('setBookData', null)
      return ''
    },
    async editBook(context, data) {
      console.log('editing book with : ', data)
      if (data) {
        try {
          const book = await axios.put(`/api/books/${data._id}`, data);
 
          return book; 
        } catch (error) {
          console.error(error);
          return error;
        }
      } 
      
      return '';
    },
    async getBook(context, id) {
      if (id) {
        try {
          const book = await axios.get(`/api/books/${id}`)
          context.commit('setEditBook', book)
          return book
        } catch (error) {
          console.error(error)
          return null
        }
      }

      return null
    }
  },
  modules: {
  }
})
