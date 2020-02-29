<template>
    <div>
    <div>
    <form>
        <p class="h4 text-center mb-4">Sign up</p>
        <div class="grey-text">
        <mdb-input label="Your name" icon="user" type="text"/>
        <mdb-input label="Your email" icon="envelope" type="email"/>
        <mdb-input label="Confirm your email" icon="exclamation-triangle" type="text"/>
        <mdb-input label="Your password" icon="lock" type="password"/>
        </div>
        <div class="text-center">
        <mdb-btn color="primary">Register</mdb-btn>
        </div>
    </form>
    </div>
    </div>
</template>

<script>
import { mdbInput, mdbBtn} from 'mdbvue';
export default {
  name: 'register',
  components: {
      mdbInput,
      mdbBtn
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