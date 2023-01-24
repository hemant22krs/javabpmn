import { useState, useRef, useContext } from 'react';
import { useNavigate } from 'react-router-dom';

import AuthContext from '../../store/auth-context';
import classes from './AuthForm.module.css';

const AuthForm = () => {
  const navigate = useNavigate();
 // const history = useHistory();
  const emailInputRef = useRef();
  const passwordInputRef = useRef();

  const authCtx = useContext(AuthContext);

  const switchAuthModeHandler = () => {
 //   setIsLogin((prevState) => !prevState);
  };

  const submitHandler = (event) => {
    event.preventDefault();

    const enteredEmail = emailInputRef.current.value;
    const enteredPassword = passwordInputRef.current.value;
    
    // optional: Add validation

   
    let url;
      url =
      'http://localhost:8081/signin';
      console.log(enteredEmail);
    fetch(url, {
      method: 'POST',
      body: JSON.stringify({
      
        username: enteredEmail,
        password: enteredPassword
      }),
      headers: {
        'Content-Type': 'application/json',
      },
    })
      .then((res) => {
      //  setIsLoading(false);
        if (res.ok) {
          return res.json();
        } else {
          return res.json().then((data) => {
            let errorMessage = 'Authentication failed!';
            throw new Error(errorMessage);
          });
        }
      })
      .then((data) => {
        authCtx.login(data.Token,data.username);
       // history.replace('/');
       navigate('/');
      })
      .catch((err) => {
        alert(err.message);
      });
  };

  return (
    <section className={classes.auth}>
      <h1>Login</h1>
      <form onSubmit={submitHandler}>

        <div className={classes.control}>
          <label htmlFor='email'>Your Email</label>
          <input type='text' id='email' required ref={emailInputRef} />
        </div>

        <div className={classes.control}>
          <label htmlFor='password'>Your Password</label>
          <input type='password' id='password' required ref={passwordInputRef} />
        </div>

        <div className={classes.actions}>
            <button>Login</button>
        </div>

      </form>
    </section>
  );
};

export default AuthForm;
