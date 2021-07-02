import axios from "axios";
import React, { useState } from "react";
import { useHistory } from "react-router";
import { link } from "../../../Proxy/proxy";

function SendMail(props) {
  const [desc, setDesc] = useState("");
  const [topic, setTopic] = useState("");
  const [message, setMessage] = useState("");
  const [showMessage, setShowMessage] = useState(false);

  const history = useHistory();

  const submitHandler = (e) => {
    const send = async () => {
      const url = `${link}admin/sendmail`;

      await axios
        .post(
          url,
          {
            to: props.to,
            body: desc,
            topic: topic,
          },
          {
            headers: {
              Authorization: `Bearer ${localStorage.getItem("token")}`,
            },
          }
        )
        .then((res) => {
          setShowMessage(true);
          setMessage(res.data.message);
        })
        .catch((err) => {
          localStorage.removeItem("token");
          alert("Session Timed Out! Login Again.");
          history.push("/login");
        });
    };

    e.preventDefault();
    setMessage("Sending Mail...");
    send();
    document.getElementById("emailform").reset();
  };

  return (
    <div>
      {showMessage && (
        <div className="alert alert-primary" role="alert">
          {message}
        </div>
      )}
      <form onSubmit={submitHandler} id="emailform">
        <label className="form-label">Subject</label>
        <input
          type="text"
          required
          className="form-control"
          maxLength="50"
          onChange={(e) => setTopic(e.target.value)}
        ></input>
        <label className="form-label">Body</label>
        <textarea
          required
          className="form-control"
          maxLength="200"
          onChange={(e) => setDesc(e.target.value)}
        ></textarea>
        <button type="submit" className="btn btn-primary my-3">
          Send
        </button>{" "}
      </form>
    </div>
  );
}

export default SendMail;
