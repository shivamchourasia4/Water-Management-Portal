import axios from "axios";
import React, { useEffect, useState } from "react";
import { useHistory } from "react-router";
import { link } from "../../../Proxy/proxy";
import WaterList from "./WaterList";

function AdminDashboard() {
  const [waterInfos, setWaterInfos] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const history = useHistory();

  useEffect(() => {
    const getInfo = async () => {
      const url = `${link}admin`;

      await axios
        .get(url, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        })
        .then((res) => {
          // console.log(res.data);
          setWaterInfos(res.data);
          setIsLoading(false);
          //   console.log(waterInfos);
        })
        .catch((err) => {
          localStorage.removeItem("token");
          alert("Session Timed Out! Login Again.");
          history.push("/login");
        });
    };

    if (localStorage.getItem("token") == null) {
      history.push("/login");
    } else if (localStorage.getItem("admin") == null) {
      history.push("/");
    } else {
      setIsLoading(true);
      getInfo();
    }
  }, [history]);

  const signout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("admin");
    history.push("/login");
  };

  return (
    <div>
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
        <nav className="navbar navbar-light bg-light">
          <div className="container-fluid">
            <span className="navbar-brand mb-0 h1">
              <h2>Water Info</h2>
            </span>
          </div>
        </nav>
        {isLoading ? (
          <h1>Loading ...</h1>
        ) : (
          <div>
            {waterInfos.map((info, index) => {
              return (
                <div key={index}>
                  <WaterList info={info} />
                </div>
              );
            })}
          </div>
        )}
      </div>
    </div>
  );
}

export default AdminDashboard;
