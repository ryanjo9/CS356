const express = require('express');
const bodyParser = require('body-parser');

const app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: false
}));

app.use(express.static('public'));

const mongoose = require('mongoose');

//connect to the database
mongoose.connect('mongodb://localhost:27017/bookstore', {
    useNewUrlParser: true
});

app.listen(3000, () => console.log('Server listening on port 3000!'));

const itemSchema = new mongoose.Schema({
    title: String,
    author: String,
    imagePath: String,
    isbn: String,
    category: String
});

const Item = mongoose.model('Item', itemSchema);

app.post('/api/save', async (req, res) => {
    try {
        const item = new Item({
            title: req.body.title,
            author: req.body.author,
            imagePath: req.body.imagePath.split(' ').join('_'),
            isbn: req.body.isbn,
            category: req.body.category
        });

        await item.save();
        res.send(item);
    } catch (error) {
        console.log(error);
        res.sendStatus(500);
    }
});

app.get('/api/books', async (req, res) => {
    try {
        let items = await Item.find();
        res.send(items);
    } catch (error) {
        console.error(error);
        res.sendStatus(500);
    }
})