<template>
<div>
  <h1>Login to your account</h1>
  <form @submit.prevent="login" class="pure-form pure-form-aligned">
    <fieldset>
      <p class="pure-form-message-inline">All fields are required.</p>

      <div class="container">
         <div class="row">
          <div class="col" />
          <div class="col-6">
            <mdbIcon icon="user" size="lg" class="icon"/>
            <mdb-input required class="input" type="text" v-model="username" placeholder="Username"/><br />
            <mdbIcon icon="lock" size="lg" class="icon"/>
            <mdb-input required class="input" type="password" v-model="password" placeholder="Password"/>
          </div>
          <div class="col" />
         </div>      
      </div>
    <br />
      <div class="container">
         <div class="row">
          <div class="col" />
          <div class="col-8">
            <mdb-btn color="primary" type="submit" class="register">Submit</mdb-btn>
            <mdb-btn color="success" type="submit">Register</mdb-btn>
          </div>
         </div>      
      </div>
    </fieldset>
  </form>
  <p v-if="error" class="error">{{error}}</p>
  <br />
  <br />

  <br />
  <br />

  <br />
  <br />

  <br />
  <br />
  <br />
  
</div>
</template>

<script>
import { mdbInput, mdbBtn, mdbIcon} from 'mdbvue';
export default {
  name: 'login',
  components: {
      mdbInput,
      mdbBtn,
      mdbIcon
    },
  data() {
    return {
      username: '',
      password: '',
      error: '',
    }
  },
  methods: {
    async login() {
      try {
        this.error = await this.$store.dispatch("login", {
          username: this.username,
          password: this.password
        });
        if (this.error === "")
          this.$router.push('/');
      } catch (error) {
        console.log(error);
      }
    }
  }
}
</script>

<style scoped>
.icon{
  margin-right: -80%;
}
.input{
  margin-top: -30px;
}
.register{
  margin-right: 10px;
}
form {
  border: 1px solid #ccc;
  background-color: #eee;
  border-radius: 4px;
  padding: 20px;
}
.pure-controls {
  display: flex;
}
.pure-controls button {
  margin-left: auto;
}
</style>