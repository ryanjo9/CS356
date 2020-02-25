<template>
  <div id="app">
    <div id="nav">
      <nav class="navbar navbar-expand-lg navbar-dark bg-dark" style="background-color: #232F3E">
        <img />
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
              <router-link style="color:white" class="nav-link" to="/">Home</router-link>
            </li>
            <li class="nav-item">
              <router-link style="color:white" class="nav-link" to="/add">Add</router-link>
            </li>
            <li class="nav-item">
              <router-link style="color:white" class="nav-link" to="/login">Login </router-link>
            </li>
          </ul>
          <form class="form-inline my-2 my-lg-0">
            <input class="form-control mr-sm-2" minlength="3"  type="search" placeholder="Search" aria-label="Search" v-model="input">
            <button class="btn btn-outline-info my-2 my-sm-0" @click="search" type="submit">Submit</button>
          </form>
        </div>
      </nav>
      <router-view/>
      <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarText">
          <ul class="navbar-nav mr-auto">
          </ul>
          <button>
            <a href="#app"> <i class="material-icons">arrow_upward</i> </a>
          </button>
        </div>
      </nav>
    </div>
  </div>
</template>

<script>
export default {
  name: 'app',
  data() {
    return {
      input: ''
    }
  },
  methods: {
    search() {
      if (this.input.length < 3) {
        return
      }
      this.$router.push({ name: 'result', params: { input: this.input }}) 
    }
  }
}
</script>

<style>
.navbar{
  background-color: #232F3E;
}
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

</style>
