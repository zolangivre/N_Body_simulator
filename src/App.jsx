import React, { useState, useEffect } from "react";
import axios from "axios";
import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import "./App.css";

const App = () => {
  const [bodyPoints, setBodyPoints] = useState([]);
  const [selectedBodyPoint, setSelectedBodyPoint] = useState(null);
  const [speed, setSpeed] = useState(100); // Vitesse par défaut
  let intervalId = null;

  const [newPoint, setNewPoint] = useState({
    name: "",
    x: 0,
    y: 0,
    z: 0,
    vitesseX: 0,
    vitesseY: 0,
    accelerationX: 0,
    accelerationY: 0,
    masse: 0,
  });

  useEffect(() => {
    axios
      .get("https://nbody-back-79c68c764a72.herokuapp.com/body")
      .then((response) => {
        setBodyPoints(response.data);
      })
      .catch((error) => {
        console.error("Erreur de récupération des points du corps:", error);
      });
  }, []);

  const simulateMovement = () => {
    axios
      .post("https://nbody-back-79c68c764a72.herokuapp.com/body/simulate")
      .then((response) => {
        setBodyPoints(response.data);
      })
      .catch((error) => {
        console.error("Erreur lors de la simulation:", error);
      });
  };

  const reset = () => {
    axios
      .post("https://nbody-back-79c68c764a72.herokuapp.com/body/reset")
      .then((response) => {
        setBodyPoints(response.data);
      })
      .catch((error) => {
        console.error("Erreur lors de la réinitialisation:", error);
      });
  };

  const deleteWithHighCoordinates = () => {
    axios
      .delete(
        "https://nbody-back-79c68c764a72.herokuapp.com/body/deleteHighCoordinates"
      )
      .then((response) => {
        setBodyPoints(response.data);
      })
      .catch((error) => {
        console.error("Erreur lors de la suppression:", error);
      });
  };

  useEffect(() => {
    if (intervalId) clearInterval(intervalId);
    intervalId = setInterval(() => {
      simulateMovement();
      deleteWithHighCoordinates();
    }, speed);
    return () => clearInterval(intervalId);
  }, [speed]);

  const handleSpeedChange = (event) => {
    setSpeed(Number(event.target.value));
  };

  return (
    <div className="App">
      <h1>N-Body Simulation</h1>

      <div>
        <h3>Vitesse de simulation</h3>
        <input
          type="range"
          min="10"
          max="500"
          value={speed}
          onChange={handleSpeedChange}
        />
        <span>{speed} ms</span>
      </div>

      <button type="button" onClick={reset}>
        Reset
      </button>

      <div className="map-container">
        <MapContainer
          center={[0, 0]}
          zoom={10}
          style={{ height: "100%", width: "1430px" }}
          crs={L.CRS.Simple}
        >
          <TileLayer url="" />

          {bodyPoints.map((point) => (
            <Marker
              key={point.id}
              position={[point.y, point.x]}
              icon={L.divIcon({
                className: "custom-icon",
                html: `<div style="background-color: ${
                  selectedBodyPoint?.id === point.id ? "red" : "blue"
                }; border-radius: 50%; width: 10px; height: 10px;"></div>`,
              })}
              eventHandlers={{
                click: () => setSelectedBodyPoint(point),
              }}
            >
              <Popup>
                <div>
                  <h3>{point.name}</h3>
                  <p>X: {point.x}</p>
                  <p>Y: {point.y}</p>
                  <p>Vitesse X: {point.vitesseX}</p>
                  <p>Vitesse Y: {point.vitesseY}</p>
                  <p>Accélération X: {point.accelerationX}</p>
                  <p>Accélération Y: {point.accelerationY}</p>
                  <p>Masse: {point.masse}</p>
                </div>
              </Popup>
            </Marker>
          ))}
        </MapContainer>
      </div>
    </div>
  );
};

export default App;
