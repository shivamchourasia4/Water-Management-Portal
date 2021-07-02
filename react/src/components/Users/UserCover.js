import React, { useEffect } from "react";
import { useHistory } from "react-router";
import { admin } from "../../Proxy/proxy";
import Dashboard from "./Dashboard";
import WaterInfo from "./WaterInfo";

function UserCover() {
  const history = useHistory();
  useEffect(() => {
    if (localStorage.getItem("token") == null) {
      history.push("/login");
    }
    if (localStorage.getItem("admin") === admin) {
      history.push("/admin");
    }
  }, [history]);

  return (
    <div>
      <Dashboard />
      <WaterInfo />
    </div>
  );
}

export default UserCover;
