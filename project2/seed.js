'use strict'

const axios = require('axios');
const seedData = require('./seed-data.json');
const books = [
  {
    title: 'The Master Algorithm',
    author: 'Pedro Domingos',
    imagePath: 'https://images-na.ssl-images-amazon.com/images/I/514G9tMBXHL._SY346_.jpg',
    isbn: '0465094279',
    category: 'Technology',
    price: '15.99',
    condition: 'Very Good'
  },
  {
    title: 'Superintelligence',
    author: 'Nick Bostrom',
    imagePath: "https://img.thriftbooks.com/api/images/l/789c753e0a3ba53c78a767276283c63b6b462521.jpg",
    isbn: '0198739834',
    category: 'Technology',
    price: '9.99',
    condition: 'Poor'
  },
  {
    title: 'The Tipping Point',
    author: 'Malcom Gladwell',
    imagePath: 'https://images-na.ssl-images-amazon.com/images/I/41xMQUGPu1L.jpg',
    isbn: '0316346624',
    category: 'Psychology',
    price: '9.99',
    condition: 'Poor'
  },
  {
    title: 'How to Win Friends & Influence People',
    author: 'Dale Carnegie',
    imagePath: 'https://images-na.ssl-images-amazon.com/images/I/51NVtjOrnqL._SY346_.jpg',
    isbn: '0671043218',
    category: 'Psychology',
    price: '12.99',
    condition: 'Good'
  },
  {
    title: 'Emotional Intelligence 2.0',
    author: 'Travis Bradberry',
    imagePath: 'https://images-na.ssl-images-amazon.com/images/I/515plB5rn9L._SY346_.jpg',
    isbn: '0974320625',
    category: 'Psychology',
    price: '19.99',
    condition: 'Like New'
  },
  {
    title: 'Outliers: The Story of Success',
    author: 'Malcom Gladwell',
    imagePath: 'https://images-na.ssl-images-amazon.com/images/I/41uvQ6ytbkL.jpg',
    isbn: '0316017930',
    category: 'Psychology',
    price: '9.99',
    condition: 'Like New'
  },
  {
    title: 'How to Win Friends & Influence People',
    author: 'Dale Carnegie',
    imagePath: 'https://covers.openlibrary.org/b/id/405180-M.jpg',
    isbn: '0671043218',
    category: 'Psychology',
    price: undefined,
    condition: 'hey'
  },
  {
    title: 'The Fear Zone',
    author: 'K. R. Alexander',
    imagePath: 'https://images-na.ssl-images-amazon.com/images/I/511Ge6inhCL._SX342_BO1,204,203,200_.jpg',
    isbn: '1338577174',
    category: 'Horror',
    price: undefined,
    condition: 'Good'
  },
  {
    title: 'Fear: 13 Stories of Suspense and Horror',
    author: ' R.L. Stine ',
    imagePath: 'https://images-na.ssl-images-amazon.com/images/I/51zCZL1EpbL._SX331_BO1,204,203,200_.jpg',
    isbn: '0142417742',
    category: 'Horror',
    price: '6.5',
    condition: 'Very Good'
  },
  {
    title: "Fear Street Super Thriller: Party Games & Don't Stay Up Late",
    author: ' R. L. Stine ',
    imagePath: 'https://images-na.ssl-images-amazon.com/images/I/51k7ojKvnpL._SX331_BO1,204,203,200_.jpg',
    isbn: ' 1250076935',
    category: 'Horror',
    price: '7.9',
    condition: 'Very good'
  },
  {
    title: 'Broken Hearts (Fear Street Super Chiller)',
    author: 'R. L. Stine',
    imagePath: 'https://images-na.ssl-images-amazon.com/images/I/515Z7wc8i3L._SX310_BO1,204,203,200_.jpg',
    isbn: '1442442735',
    category: 'Fiction & Fantasy',
    price: undefined,
    condition: 'Bad'
  },
  {
    title: 'The rich girl',
    author: 'R. L. Stine',
    imagePath: 'https://covers.openlibrary.org/b/id/406082-M.jpg',
    isbn: '0671529625',
    category: 'Fiction & Fantasy',
    price: '6.5',
    condition: 'very good'
  },
  {
    title: 'How the mind works',
    author: 'Steven Pinker',
    imagePath: 'https://covers.openlibrary.org/b/id/8659943-M.jpg',
    isbn: '0393318486',
    category: 'Psychology',
    price: '18.5',
    condition: 'good'
  },
  {
    title: 'Rick Steves Italy 2020',
    author: 'Rick Steves',
    imagePath: 'https://images-na.ssl-images-amazon.com/images/I/51lclGw6sxL._SX279_BO1,204,203,200_.jpg',
    isbn: '164171154X',
    category: 'Journal',
    price: '18.99',
    condition: 'New'
  },
  {
    title: 'The Oxford companion to World War II',
    author: 'Ian Dear',
    imagePath: 'https://covers.openlibrary.org/b/id/132009-M.jpg',
    isbn: '978-0198662259',
    category: 'History',
    price: undefined,
    condition: 'Like New'
  },
  {
    title: 'World War II: The Definitive Visual History from Blitzkrieg to the Atom Bomb',
    author: 'DK',
    imagePath: 'https://images-na.ssl-images-amazon.com/images/I/5118hL-huIL._SX417_BO1,204,203,200_.jpg',
    isbn: '978-1465436023',
    category: 'History',
    price: undefined,
    condition: 'New'
  },
  {
    title: 'The First World War: A Complete History',
    author: 'Martin Gilber',
    imagePath: 'https://images-na.ssl-images-amazon.com/images/I/51jj2BRYPpL._SX322_BO1,204,203,200_.jpg',
    isbn: '978-0805076172',
    category: 'History',
    price: undefined,
    condition: 'Good'
  },
  {
    title: 'Lady First: The World of First Lady Sarah Polk',
    author: 'Amy S. Greenberg',
    imagePath: 'https://images-na.ssl-images-amazon.com/images/I/51ecQJJm1fL._SX334_BO1,204,203,200_.jpg',
    isbn: ' 978-0385354134',
    category: 'History',
    price: undefined,
    condition: 'Bad'
  },
  {
    title: 'A Wicked War: Polk, Clay, Lincoln, and the 1846 U.S. Invasion of Mexico',
    author: 'Amy S. Greenberg',
    imagePath: 'https://images-na.ssl-images-amazon.com/images/I/510pHmPLcrL._SX322_BO1,204,203,200_.jpg',
    isbn: '978-0307475992',
    category: 'History',
    price: '7.8',
    condition: 'Good'
  },
  {
    title: 'Chanel: Collections and Creations',
    author: 'Daniele Bott',
    imagePath: 'https://images-na.ssl-images-amazon.com/images/I/4115Asfc%2BgL._SX425_BO1,204,203,200_.jpg',
    isbn: '978-0500513606',
    category: 'Art',
    price: '15.99',
    condition: 'Good'
  },
  {
    title: 'Tom Ford',
    author: 'Tom Ford',
    imagePath: 'https://images-na.ssl-images-amazon.com/images/I/31CJhwW0BIL._SX382_BO1,204,203,200_.jpg',
    isbn: '978-0847826698',
    category: 'Art',
    price: '50.69',
    condition: 'Good'
  },
  {
    title: 'Building a Life Worth Living: A Memoir',
    author: 'Marsha M. Linehan ',
    imagePath: 'https://images-na.ssl-images-amazon.com/images/I/51d-DCT%2BO9L._SX327_BO1,204,203,200_.jpg',
    isbn: '978-0812994612',
    category: 'Memoir',
    price: '7.89',
    condition: 'Very Good'
  },
  {
    title: 'The Magical Language of Others: A Memoir',
    author: 'E. J. Koh',
    imagePath: 'https://images-na.ssl-images-amazon.com/images/I/4195Np4KRDL._SX322_BO1,204,203,200_.jpg',
    isbn: ' 978-1947793385',
    category: 'Memoir',
    price: '5.6',
    condition: 'Very Good'
  },
  {
    title: 'Flour Water Salt Yeast',
    author: 'Ken Forkish',
    imagePath: 'https://covers.openlibrary.org/b/id/8163031-M.jpg',
    isbn: '9781607742739',
    category: 'Cookbook',
    price: '13.99',
    condition: 'Good'
  },
  {
    title: 'Mediterranean Diet Cookbook: 550 Quick, Easy and Healthy Mediterranean Diet Recipes for Everyday Cooking',
    author: 'Liam Sandler',
    imagePath: 'https://images-na.ssl-images-amazon.com/images/I/51qxwAEfPvL._SX385_BO1,204,203,200_.jpg',
    isbn: '9781094755175',
    category: 'Cookbook',
    price: '6.99',
    condition: 'Very Good'
  },
  {
    title: 'The Immortal Life of Henrietta Lacks',
    author: 'Rebecca Skloot',
    imagePath: 'https://covers.openlibrary.org/b/id/8364866-M.jpg',
    isbn: '9781400052189',
    category: 'Biography',
    price: undefined,
    condition: 'Good'
  },
  {
    title: 'Thomas Edison: A Captivating Guide to the Life of a Genius Inventor',
    author: 'Captivating History ',
    imagePath: 'https://images-na.ssl-images-amazon.com/images/I/51-FK4VKwsL.jpg',
    isbn: '1984141821',
    category: 'Biography',
    price: '12.36',
    condition: 'Very Good'
  },
  {
    title: 'The Adventures of Huckleberry Finn ',
    author: 'Mark Twain',
    imagePath: 'https://images-na.ssl-images-amazon.com/images/I/41px-cp7PZL.jpg',
    isbn: '1986790045',
    category: 'Action and adventure',
    price: '2.36',
    condition: 'Good'
  },
  {
    title: 'Deception (The Transformed) (Volume 1)',
    author: 'Stacy Claflin',
    imagePath: 'https://images-na.ssl-images-amazon.com/images/I/51rjcr3BF0L._SX322_BO1,204,203,200_.jpg',
    isbn: '9781545006894',
    category: 'Fiction & Fantasy',
    price: '10.89',
    condition: 'Very Good'
  },
  {
    title: 'The Adventures of Tom Sawyer',
    author: 'Mark Twain',
    imagePath: 'https://images-na.ssl-images-amazon.com/images/I/51qAZk60G%2BL._SX331_BO1,204,203,200_.jpg',
    isbn: '9781514682050',
    category: 'Action and adventure',
    price: undefined,
    condition: 'Very Good'
  },
  {
    title: "The Classic Treasury of Aesop's Fables",
    author: 'Don Daily',
    imagePath: 'https://covers.openlibrary.org/b/id/1434043-M.jpg',
    isbn: '9780762428762',
    category: "Children's",
    price: '5.69',
    condition: 'Good'
  },
  {
    title: "Aesop's Fables: 240 Short Stories for Children - Illustrated ",
    author: 'Aesop',
    imagePath: 'https://images-na.ssl-images-amazon.com/images/I/51DM5uYLhZL._SX398_BO1,204,203,200_.jpg',
    isbn: '9781450502955',
    category: "Children's",
    price: '3.59',
    condition: 'Good'
  }
]

const run = async () => {
  console.log('Saving books')
  await Promise.all(seedData.data.map((book) => {
    return axios.post('http://localhost:3000/api/save', book)
  }))
  console.log('Finished!');
}

run().catch(console.error)
