import { Route, Routes } from 'react-router-dom';
import { useContext } from 'react';
import Layout from './components/Layout/Layout';
import UserProfile from './components/Profile/UserProfile';
import HomePage from './pages/HomePage';
import AdminUser from './pages/AdminUser';
import Committee from './pages/Committee';
import Participant from './pages/Participant';
import AuthContext from './store/auth-context';

function App() {
  const authCtx = useContext(AuthContext);

  return (
    <Layout>
      <Routes>
        <Route path='/' element={ <HomePage />} exact>      
        </Route>
        {!authCtx.isLoggedIn && (
        <Route path='/admin'  element={ <AdminUser />}> 
        </Route>
        )}
         {!authCtx.isLoggedIn && (
         <Route path='/committee'  element={ <Committee />} >         
         </Route>
         )}
         {authCtx.isLoggedIn && (
        <Route path='/profile' element={ <UserProfile />}> 
        </Route>
         )}    
        <Route path='/participant'  element={ <Participant />} >         
        </Route>
      </Routes>
    </Layout>
  );
}

export default App;
