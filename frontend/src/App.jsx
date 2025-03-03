import React, { useState, useEffect } from "react";
import axios from "axios";
import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import "./App.css";

const App = () => {
  const [bodyPoints, setBodyPoints] = useState([]);
  const [selectedBodyPoint, setSelectedBodyPoint] = useState(null);

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
      .get("http://localhost:8080/body")
      .then((response) => {
        setBodyPoints(response.data);
        console.log("Points du corps récupérés:", response.data);
      })
      .catch((error) => {
        console.error("Erreur de récupération des points du corps:", error);
      });
  }, []);

  const simulateMovement = () => {
    axios
      .post("http://localhost:8080/body/simulate")
      .then((response) => {
        setBodyPoints(response.data);
      })
      .catch((error) => {
        console.error("Erreur lors de la simulation:", error);
      });
  };

  useEffect(() => {
    const intervalId = setInterval(() => {
      simulateMovement();
    }, 100);
    return () => clearInterval(intervalId);
  }, []);

  const addNewPoint = () => {
    axios
      .post("http://localhost:8080/body", newPoint)
      .then((response) => {
        setBodyPoints([...bodyPoints, response.data]);
        setNewPoint({
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
      })
      .catch((error) => {
        console.error("Erreur lors de l'ajout du point:", error);
      });
  };

  return (
    <div className="App">
      <h1>Simulation en Temps Réel des Points du Corps</h1>

      <div>
        <h3>Créer un nouveau point</h3>
        <button type="button" onClick={addNewPoint}>
          Ajouter un point
        </button>
      </div>

      <div className="map-container">
        <MapContainer
          center={[0, 0]}
          zoom={10}
          style={{ height: "100%", width: "1430px" }}
          crs={L.CRS.Simple}
          minZoom={-Infinity}
          maxZoom={Infinity}
        >
          <TileLayer url="" />

          {bodyPoints.map((point) => {
            return (
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
                    <p>Z: {point.z}</p>
                    <p>Vitesse X: {point.vitesseX}</p>
                    <p>Vitesse Y: {point.vitesseY}</p>
                    <p>Accélération X: {point.accelerationX}</p>
                    <p>Accélération Y: {point.accelerationY}</p>
                    <p>Masse: {point.masse}</p>
                  </div>
                </Popup>
              </Marker>
            );
          })}
        </MapContainer>
      </div>
    </div>
  );
};

export default App;
