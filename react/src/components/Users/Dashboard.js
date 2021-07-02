import axios from "axios";
import React, { useState } from "react";
import { link } from "../../Proxy/proxy";
import { useHistory } from "react-router";

function Dashboard() {
  const [area, setArea] = useState("");
  const [city, setCity] = useState("");
  const [duration, setDuration] = useState("");
  const [pressure, setPressure] = useState("");
  const [desc, setDesc] = useState("");
  const [showMessage, setShowMessage] = useState(false);
  const [message, setMessage] = useState("");

  const history = useHistory();
  const addInfoReq = (e) => {
    const addInfo = async () => {
      const url = `${link}addInfo`;

      await axios
        .post(
          url,
          {
            waterPressure: pressure,
            waterDesc: desc,
            location: area,
            city: city,
            duration: duration,
          },
          {
            headers: {
              Authorization: `Bearer ${localStorage.getItem("token")}`,
            },
          }
        )
        .then((res) => {
          // console.log(res.data);
          setMessage(res.data.message);
          setShowMessage(true);
          document.getElementById("formdashboard").reset();
        })
        .catch((err) => {
          localStorage.removeItem("token");
          alert("Session Timed Out! Login Again.");
          history.push("/login");
        });
    };
    e.preventDefault();
    addInfo();
  };

  const signout = () => {
    localStorage.removeItem("token");
    history.push("/login");
  };

  return (
    <div className="cover-content">
      <nav className="navbar navbar-dark bg-dark ">
        <div className="container-fluid ">
          <span className="navbar-brand mb-0 h1">
            {" "}
            <h1>Water Management Portal</h1>
          </span>
          <span>
            <button className="btn btn-danger" onClick={signout}>
              Sign Out
            </button>
          </span>
        </div>
      </nav>
      <div>
        <nav className="navbar navbar-light bg-light">
          <div className="container-fluid">
            <span className="navbar-brand mb-0 h1">
              <h2>Add Water Info</h2>
            </span>
          </div>
        </nav>
        {showMessage && (
          <div className="alert alert-primary" role="alert">
            {message}
          </div>
        )}
        <div className="container">
          <form onSubmit={addInfoReq} id="formdashboard">
            <label className="form-label">Area</label>
            <input
              type="text"
              required
              className="form-control"
              maxLength="100"
              onChange={(e) => setArea(e.target.value)}
            ></input>
            <label className="form-label">City</label>
            <input
              type="text"
              required
              maxLength="30"
              className="form-control"
              onChange={(e) => setCity(e.target.value)}
            ></input>
            <label className="form-label">Duration</label>
            <input
              type="number"
              min="0"
              max="300"
              className="form-control"
              required
              onChange={(e) => setDuration(e.target.value)}
            ></input>
            mins
            <br></br>
            <label className="form-label">Pressure</label>
            <input
              type="number"
              min="0"
              max="100"
              required
              onChange={(e) => setPressure(e.target.value)}
              className="form-control"
            ></input>
            PSI
            <br></br>
            <label className="form-label">Description</label>
            <textarea
              required
              maxLength="200"
              onChange={(e) => setDesc(e.target.value)}
              className="form-control"
            ></textarea>
            <button type="submit" className="btn btn-primary my-3">
              Add
            </button>
          </form>
        </div>
        <hr />
      </div>
    </div>
  );
}

export default Dashboard;
