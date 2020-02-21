'use strict'

const axios = require('axios');

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
    imagePath: 'data:image/jpg;base64,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',
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
  await Promise.all(books.map((book) => {
    return axios.post('http://localhost:3000/api/save', book)
  }))
  console.log('Finished!');
}

run().catch(console.error)
