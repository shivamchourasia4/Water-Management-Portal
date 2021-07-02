import axios from "axios";
import React, { useState } from "react";
import { useHistory } from "react-router";
import { link } from "../../Proxy/proxy";

function SignUp() {
  const history = useHistory();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confpassword, setconfPassword] = useState("");
  const [firstName, setfirstName] = useState("");
  const [mobileNumber, setMobileNumber] = useState("");
  const [lastName, setlastName] = useState("");
  const [message, setMessage] = useState("");
  const [showMessage, setShowMessage] = useState(false);

  const reqSignUp = (e) => {
    e.preventDefault();

    const signup = async () => {
      const url = `${link}/signup`;

      await axios
        .post(url, {
          email: email,
          password: password,
          firstName: firstName,
          lastName: lastName,
          mobileNumber: mobileNumber,
          active: "true",
          role: "employee",
        })
        .then((res) => {
          localStorage.setItem("token", res.data[1].token);
          history.push("/");
        })
        .catch((err) => {
          //   console.log(err.response.data[0].error);
          setMessage(err.response.data[0].error);
          setShowMessage(true);
        });
    };

    if (password === confpassword) {
      signup();
    } else {
      setShowMessage(true);
      setMessage("Passwords Do Not Match!");
    }
  };
  return (
    <div className="cover">
      <div className="container">
        <h1 className="h3 mb-3 fw-normal">Water Management Portal</h1>
        <h1 className="h1 mb-3 fw-normal">Sign Up</h1>
        {showMessage && (
          <div className="alert alert-danger" role="alert">
            {message}
          </div>
        )}
        <form onSubmit={reqSignUp}>
          <label className="form-label">First Name</label>
          <input
            type="text"
            maxLength="15"
            onChange={(e) => setfirstName(e.target.value)}
            required
            className="form-control"
          ></input>
          <label className="form-label">Last Name</label>
          <input
            type="text"
            maxLength="15"
            onChange={(e) => setlastName(e.target.value)}
            required
            className="form-control"
          ></input>
          <label className="form-label">Email</label>
          <input
            type="email"
            onChange={(e) => setEmail(e.target.value)}
            required
            className="form-control"
          ></input>
          <label className="form-label">Mobile Number</label>
          <input
            type="text"
            maxLength="10"
            onChange={(e) => setMobileNumber(e.target.value)}
            required
            className="form-control"
          ></input>
          <label className="form-label">Password</label>
          <input
            type="password"
            maxLength="30"
            onChange={(e) => setPassword(e.target.value)}
            required
            className="form-control"
          ></input>
          <label className="form-label">Confirm Password</label>
          <input
            type="password"
            maxLength="30"
            onChange={(e) => setconfPassword(e.target.value)}
            required
            className="form-control"
          ></input>
          <button type="submit" className="btn btn-primary my-3">
            Sign Up
          </button>
        </form>
        <a href="/login">Already Have An Account?</a>
      </div>
    </div>
  );
}

export default SignUp;
