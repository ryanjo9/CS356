'use strict'

const axios = require('axios');

const books = [{
  // psychology
  title: 'How to Win Friends & Influence People',
  author: 'Dale Carnegie',
  isbn: '0671043218',
  category: 'Psychology',
  price: 12.99,
  condition: 'Good'
}, {
  title: 'The Tipping Point',
  author: 'Malcom Gladwell',
  isbn: '0316346624',
  category: 'Psychology',
  price: 9.99,
  condition: 'Poor'
}, {
  // technology
  title: 'The Master Algorithm',
  author: 'Pedro Domingos',
  isbn: '0465094279',
  category: 'Technology',
  price: 15.99,
  condition: 'Very Good'
}, {
  title: 'Superintelligence',
  author: 'Nick Bostrom',
  isbn: '0198739834',
  category: 'Technology',
  price: 9.99,
  condition: 'Poor'
}, {
  title: 'The Tipping Point',
  author: 'Malcom Gladwell',
  isbn: '0316346624',
  category: 'Psychology',
  price: 9.99,
  condition: 'Poor'
}, {
  title: 'Outliers: The Story of Success',
  author: 'Malcom Gladwell',
  isbn: '0316017930',
  category: 'Psychology',
  price: 9.99,
  condition: 'Like New'
}, {
  title: 'Outliers: The Story of Success',
  author: 'Malcom Gladwell',
  isbn: '0316017930',
  category: 'Psychology',
  price: 19.99,
  condition: 'Like New'
}, {
  title: 'Emotional Intelligence 2.0',
  author: 'Travis Bradberry',
  isbn: '0974320625',
  category: 'Psychology',
  price: 19.99,
  condition: 'Like New'
}]

const run = async () => {
  console.log('Saving books')
  await Promise.all(books.map((book) => {
    return axios.post('http://localhost:3000/api/save', book)
  }))
  console.log('Finished!');
}

run().catch(console.error)
