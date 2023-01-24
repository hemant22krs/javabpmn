import React, { useState } from 'react';

const AuthContext = React.createContext({
  token: '',
  username: '',
  isLoggedIn: false,
  login: (token,username) => {
    this.token = token;
    this.username = username;
  },
  logout: () => {},
});

export const AuthContextProvider = (props) => {
  const [token, setToken] = useState(null);
  const [username, setUsername] = useState(null);
  console.log(token);
  const userIsLoggedIn = !!token;

  const loginHandler = (token,username) => {
    setToken(token);
    setUsername(username);
  };

  const logoutHandler = () => {
    setToken(null);
    setUsername(null);
  };

  const contextValue = {
    token: token,
    username:username,
    isLoggedIn: userIsLoggedIn,
    login: loginHandler,
    logout: logoutHandler,
  };

  return (
    <AuthContext.Provider value={contextValue}>
      {props.children}
    </AuthContext.Provider>
  );
};

export default AuthContext;