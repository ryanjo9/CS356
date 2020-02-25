<template>
  <div class="home">
    <!-- This is just to remind us that it's there -->
    
    <ul id="example-2">
    <span class="list" v-for="(book, position) in books" v-bind:key="position">
        <img :src="book.imagePath" class="picture"/>
        <section class="box2">
          <h1 style="font-size:15px">{{ book.title }} </h1>  
          <h2 style="font-size:10px"> Author: {{book.author}} </h2> 
          <h2 style="font-size:10px"> Condition: {{ book.condition }}</h2>
          <h2 style="font-size:10px">Price: {{ book.price }} </h2>
        </section>
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
<style scoped>
.list{
  display: flex;
  align-items: center;
  flex-flow: row nowrap;
  overflow: auto;
} 
.picture{
  width:150px;
  padding: 5px;
}
.box{
  display: flex;
}
</style>