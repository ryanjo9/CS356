<template>
  <div class="home">
    <!-- This is just to remind us that it's there -->
    <div>
    <ul id="example-2">
      <span class="list">
        <section v-for="(book, position) in books" v-bind:key="position">
              <img  class="picture" :src="book.imagePath"/>
              <section class="box2">
              <h1 style="font-size:15px">{{ book.title }} </h1>  
              <h2 style="font-size:10px"> Author: {{book.author}} </h2> 
              <h2 style="font-size:10px"> Condition: {{ book.condition }}</h2>
              <h2 style="font-size:10px">Price: {{ book.price }} </h2>
              </section>
        </section>
      </span>
    </ul>
    </div>
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
  height: 200px;
  padding: 5px;
  position: relative;
  margin-left: 5px;
  display: flex;
}
.box2{
  position: static;
  display: table;
  width:150px;
  height: 180px;
}
h1{
  font-weight: bold;
}
</style>