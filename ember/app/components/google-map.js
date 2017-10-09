import Ember from 'ember';
import $ from 'jquery';

export default Ember.Component.extend({
  editableMarker: false,
  editableBounds: false,

  didInsertElement(...args) {
    this._super(...args);
    this.renderGoogleMap($('.map-canvas')[0]);
  },

  renderGoogleMap(container) {
    let options = {
      center: new window.google.maps.LatLng(43.9, 18),
      zoom: 7,
    };

    let map = new window.google.maps.Map(container, options);

    let bounds = (typeof this.get('bounds') !== 'undefined') ? JSON.parse(this.get('bounds')) : null;
    let coords = (bounds !== null && bounds.length > 0) ? bounds :
    [
      { lat: 44.773, lng: 17.244 },
      { lat: 43.624, lng: 17.310 },
      { lat: 43.630, lng: 18.904 },
      { lat: 44.773, lng: 18.907 },
    ];

    let zoomBounds = new google.maps.LatLngBounds();
    for (let coordiante in coords) {
      if (coords.hasOwnProperty(coordiante)) {
        zoomBounds.extend(new google.maps.LatLng(coords[coordiante].lat, coords[coordiante].lng));
      }
    }

    map.fitBounds(zoomBounds);

    let markerPosition;
    if (this.get('markerLat') === 0 && this.get('markerLng') === 0) {
      markerPosition = zoomBounds.getCenter();
      this.set('defaultMerkerPosition', markerPosition);
    } else {
      markerPosition = new google.maps.LatLng(this.get('markerLat'), this.get('markerLng'));
    }

    let marker = new google.maps.Marker({
      position: markerPosition,
      draggable: this.get('editableMarker'),
    });

    marker.setMap(map);
    this.set('marker', marker);

    let polygon = new google.maps.Polygon({
      paths: coords,
      strokeColor: '#fd6f60',
      strokeOpacity: 0.8,
      strokeWeight: 2,
      fillColor: '#fd6f60',
      fillOpacity: 0.1,
      editable: this.get('editableBounds'),
      draggable: this.get('editableBounds'),
      geodesic: true,
    });

    polygon.setMap(map);
    this.set('polygon', polygon);
  }
});
