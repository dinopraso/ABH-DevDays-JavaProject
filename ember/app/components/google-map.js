import Ember from 'ember';
import $ from 'jquery';

export default Ember.Component.extend({
  editableMarker: false,
  editableBounds: false,
  zoomMap: 13,
  
  didInsertElement(...args) {
    this._super(...args);
    this.renderGoogleMap($('.map-canvas')[0]);
  },

  renderGoogleMap(container) {
    let options = {
      center: new window.google.maps.LatLng(43.9, 18),
      scrollwheel: true,
      zoom: this.get('zoomMap'),
      mapTypeId: google.maps.MapTypeId.ROADMAP,
    };

    let map = new window.google.maps.Map(container, options);

    let bounds = (typeof this.get('bounds') !== 'undefined') ? JSON.parse(this.get('bounds')) : null;
    let coords = (bounds !== null && bounds.length > 0) ? bounds :
    [
      { lat: 43.702, lng: 18.115 },
      { lat: 43.987, lng: 18.115 },
      { lat: 43.987, lng: 18.631 },
      { lat: 43.702, lng: 18.631 },
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
      tittle: 'Location',
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
