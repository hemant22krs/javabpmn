import { Link } from 'react-router-dom';
import { useContext } from 'react';
import classes from './MainNavigation.module.css';
import AuthContext from '../../store/auth-context';

const MainNavigation = () => {
  const authCtx = useContext(AuthContext);

  const isLoggedIn = authCtx.isLoggedIn;

  const logoutHandler = () => {
    authCtx.logout();
    // optional: redirect the user
  };


  return (
    <header className={classes.header}>
      <Link to='/'>
        <div className={classes.logo}>AllState India</div>
      </Link>
      <nav>
        <ul>
        {!isLoggedIn && (
          <li>
            <Link to='/admin'>Admin</Link>
          </li>
          )}

          {!isLoggedIn && (
          <li>
          <Link to='/committee'>Committee</Link>
        </li>
         )}

          {!isLoggedIn && (
         <li>
         <Link to='/participant'>Participant</Link>
       </li>
        )}

        {isLoggedIn && (
              <li>
              <Link to='/profile'>Profile</Link>
            </li>
        )}
         
         {isLoggedIn && (
            <li>
              <button onClick={logoutHandler}>Logout</button>
            </li>
          )}

        </ul>
      </nav>
    </header>
  );
}

export default MainNavigation;
