import React, { useState } from "react";
import axios from "axios";
import { link } from "../../Proxy/proxy";
import { useHistory } from "react-router";

function Login() {
  const history = useHistory();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [resp, setResp] = useState("");
  const [showMessage, setShowMessage] = useState(false);

  const reqLogin = (e) => {
    e.preventDefault();

    const login = async () => {
      const url = `${link}/login`;

      await axios
        .post(url, {
          email: email,
          password: password,
        })
        .then((res) => {
          localStorage.setItem("token", res.data[1].token);
          if (email === "watermgmt002@gmail.com") {
            localStorage.setItem("admin", email);
            history.push("/admin");
          } else {
            history.push("/");
          }
        })
        .catch((err) => {
          if (err.response.status === 400) {
            setResp("Invalid Credentials!");
            setShowMessage(true);
          }
        });
    };

    login();
  };

  return (
    <div className="cover">
      <div className="container">
        <h1 className="h3 mb-3 fw-normal">Water Managemnt Portal</h1>
        <h1 className="h1 mb-3 fw-normal">Sign In</h1>
        {showMessage && (
          <div className="alert alert-danger" role="alert">
            {resp}
          </div>
        )}
        <form onSubmit={reqLogin}>
          <label htmlFor="exampleInputEmail1" className="form-label">
            Email
          </label>
          <input
            type="email"
            required
            className="form-control"
            onChange={(e) => setEmail(e.target.value)}
          ></input>
          <label htmlFor="exampleInputPassword1" className="form-label">
            Password
          </label>
          <input
            type="password"
            maxLength="30"
            required
            className="form-control"
            onChange={(e) => setPassword(e.target.value)}
          ></input>

          <button type="submit" className="btn btn-primary my-3">
            Login
          </button>
        </form>
        <a href="/signup">Don't Have An Account?</a>
      </div>
    </div>
  );
}

export default Login;
