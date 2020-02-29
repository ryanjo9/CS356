'use strict';

const fs = require('fs');
const fileData = require('./update.json');

const { data } = Object.assign({}, fileData);



const formattedData = data.map((item) => {
  return {
    title: item.title,
    author: item.author,
    imagePath: item.imagePath,
    isbn: item.isbn,
    category: item.category,
    price: item.price['$numberDouble'],
    condition: item.condition
  }
})

fs.writeFileSync('./seed-data.json', JSON.stringify({ data: formattedData}));
