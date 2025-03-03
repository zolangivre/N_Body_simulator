import React, { useState, useEffect } from "react";
import axios from "axios";
import { MapContainer, TileLayer, Marker, Popup, useMap } from "react-leaflet";
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

  const limitCoordinates = (x, y) => {
    const minX = -1000,
      maxX = 1000;
    const minY = -1000,
      maxY = 1000;
    const limitedX = Math.min(Math.max(x, minX), maxX);
    const limitedY = Math.min(Math.max(y, minY), maxY);
    return [limitedX, limitedY];
  };

  const MapUpdater = () => {
    const map = useMap();
    const bounds = bodyPoints.map((point) => [point.y, point.x]);
    if (bounds.length > 0) {
      map.fitBounds(bounds);
    }
    return null;
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
          zoom={2}
          style={{ height: "100%", width: "100%" }}
        >
          <TileLayer url="https:" />
          {bodyPoints.map((point) => {
            const [limitedX, limitedY] = limitCoordinates(point.x, point.y);
            return (
              <Marker
                key={point.id}
                position={[limitedY, limitedX]}
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
                <Popup>{point.name}</Popup>
              </Marker>
            );
          })}
        </MapContainer>
      </div>
    </div>
  );
};

export default App;
