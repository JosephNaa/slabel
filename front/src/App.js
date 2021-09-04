import React, { useState, useEffect } from "react";
import { Link, Route, Switch, BrowserRouter as Router } from "react-router-dom";
import homePage from "./pages/HomePage/HomePage";
import aboutPage from "./pages/AboutPage/AboutPage";
import loginPage from "./pages/LoginPage/LoginPage";
import axios from "axios";
import 'antd/dist/antd.css';
const JWT_EXPIRY_TIME = 24 * 3600 * 1000; // 만료 시간 (24시간 밀리 초로 표현)

const onLoginSuccess = response => {
  const { accessToken } = response.data;

  // accessToken 설정
  axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;

  // accessToken 만료하기 1분 전에 로그인 연장
  setTimeout(onSilentRefresh, JWT_EXPIRY_TIME - 60000);
}

const onSilentRefresh = () => {
  axios.post('/silent-refresh')
    .then(onLoginSuccess)
    .catch(error => <Link to="/login" />);
}
function App() {

  useEffect(() =>{
    onSilentRefresh();
  },[]);

  return (
    <Router>
      <header>
        <Link to="/">
          <button>Home</button>
        </Link>
        <Link to="/login">
          <button>Login</button>
        </Link>
        <Link to="/about">
          <button>About</button>
        </Link>
      </header>
      <hr />
      <main>
        <Switch>
          <Route exact path="/" component={homePage} />
          <Route path="/about" component={aboutPage} />
          <Route path="/login" component={loginPage} />
        </Switch>
      </main>
    </Router>
  );
}

export default App;