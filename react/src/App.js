import "./App.css";
import { Route, Switch, BrowserRouter } from "react-router-dom";
import Login from "./components/Users/Login";
import SignUp from "./components/Users/SignUp";
import AdminDashboard from "./components/admin/Component1/AdminDashboard";
import UserCover from "./components/Users/UserCover";

function App() {
  return (
    <BrowserRouter>
      <Switch>
        <Route path="/login" exact>
          <Login />
        </Route>
        <Route path="/signup" exact>
          <SignUp />
        </Route>
        <Route path="/" exact>
          <UserCover />
        </Route>
        <Route path="/admin" exact>
          <AdminDashboard />
        </Route>
        <Route path="">
          <h1>ERROR 404 Page Not Found!!</h1>
        </Route>
      </Switch>
    </BrowserRouter>
  );
}

export default App;
