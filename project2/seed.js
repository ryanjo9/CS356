'use strict'

const axios = require('axios');

const books = [{
  // psychology
  title: 'How to Win Friends & Influence People',
  author: 'Dale Carnegie',
  isbn: '0671043218',
  category: 'Psychology',
  price: 12.99,
  condition: 'Good',
  imagePath: 'https://images-na.ssl-images-amazon.com/images/I/51NVtjOrnqL._SY346_.jpg'
}, {
  title: 'The Tipping Point',
  author: 'Malcom Gladwell',
  isbn: '0316346624',
  category: 'Psychology',
  price: 9.99,
  condition: 'Poor',
  imagePath: 'https://images-na.ssl-images-amazon.com/images/I/41xMQUGPu1L.jpg'
}, {
  // technology
  title: 'The Master Algorithm',
  author: 'Pedro Domingos',
  isbn: '0465094279',
  category: 'Technology',
  price: 15.99,
  condition: 'Very Good',
  imagePath: 'https://images-na.ssl-images-amazon.com/images/I/514G9tMBXHL._SY346_.jpg'
}, {
  title: 'Superintelligence',
  author: 'Nick Bostrom',
  isbn: '0198739834',
  category: 'Technology',
  price: 9.99,
  condition: 'Poor',
  imagePath: 'https://images-na.ssl-images-amazon.com/images/I/514G9tMBXHL._SY346_.jpg'
}, {
  title: 'The Tipping Point',
  author: 'Malcom Gladwell',
  isbn: '0316346624',
  category: 'Psychology',
  price: 9.99,
  condition: 'Poor',
  imagePath: 'https://images-na.ssl-images-amazon.com/images/I/41xMQUGPu1L.jpg'
}, {
  title: 'Outliers: The Story of Success',
  author: 'Malcom Gladwell',
  isbn: '0316017930',
  category: 'Psychology',
  price: 9.99,
  condition: 'Like New',
  imagePath: 'https://images-na.ssl-images-amazon.com/images/I/41uvQ6ytbkL.jpg'
}, {
  title: 'Outliers: The Story of Success',
  author: 'Malcom Gladwell',
  isbn: '0316017930',
  category: 'Psychology',
  price: 19.99,
  condition: 'Like New',
  imagePath: 'https://images-na.ssl-images-amazon.com/images/I/41uvQ6ytbkL.jpg'
}, {
  title: 'Emotional Intelligence 2.0',
  author: 'Travis Bradberry',
  isbn: '0974320625',
  category: 'Psychology',
  price: 19.99,
  condition: 'Like New',
  imagePath: 'https://images-na.ssl-images-amazon.com/images/I/515plB5rn9L._SY346_.jpg'
}]

const run = async () => {
  console.log('Saving books')
  await Promise.all(books.map((book) => {
    return axios.post('http://localhost:3000/api/save', book)
  }))
  console.log('Finished!');
}

run().catch(console.error)
