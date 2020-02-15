<template>
  <div class="home">
    <!-- This is just to remind us that it's there -->
    
    <ul id="example-2">
    <span v-for="(book, position) in books" v-bind:key="position">
        <div class="polaroid">
        <img :src="book.imagePath"/>
        {{ book.title }} - {{book.author}}
        Condition: {{ book.condition }}
        Price: {{ book.price }}
        </div>
    </span>
    </ul>
  </div>
</template>

<script>

export default {
  name: 'result',
  data() {
    return {
      books: [],
      value: String
    }
  },
  async created() {
    this.books = await this.$store.dispatch('getBooks');
    this.value = this.$route.params.input.toLowerCase();
    this.books = this.books.filter((book) => {
        let add = false;
        Object.values(book).forEach((value) => {
            if (typeof value === 'string' && value.toLowerCase().includes(this.value)) {
                add = true
            }
        })

        return add
    })
  }
}
</script>
<style>

</style>
