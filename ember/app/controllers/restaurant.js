import Ember from 'ember';
import $ from 'jquery';

const {
  inject: {
    service,
  },
  computed,
  computed: {
    alias,
    gt,
  },
} = Ember;

export default Ember.Controller.extend({
  ajax: service('ajax'),

  numberOfPeople: 1,
  review_text: '',
  review_score: 0,

  areAvailableTables: gt('model.response.numberOfTablesLeft', 0),

  breakfastMenu: computed('model.restaurant.menu', function () {
    return JSON.parse(this.get('model.restaurant.menu')).breakfast;
  }),

  lunchMenu: computed('model.restaurant.menu', function () {
    return JSON.parse(this.get('model.restaurant.menu')).lunch;
  }),

  dinnerMenu: computed('model.restaurant.menu', function () {
    return JSON.parse(this.get('model.restaurant.menu')).dinner;
  }),

  hasMap: computed('model.restaurant.latitude', 'model.restaurant.longitude', function () {
    return this.get('model.restaurant.latitude') !== 0 && this.get('model.restaurant.longitude') !== 0;
  }),

  openTime: computed('model.restaurant.openTime', function () {
    let time = this.get('model.restaurant.openTime').split(':');
    return time[0] + ':' + time[1];
  }),

  closeTime: computed('model.restaurant.closeTime', function () {
    let time = this.get('model.restaurant.closeTime').split(':');
    return time[0] + ':' + time[1];
  }),

  date: new Date().toISOString().substring(0, 10),
  today: alias('date'),

  time: '17:30',

  actions: {
    showRatingDialog() {
      let myReview = this.get('model.restaurant.reviews').find((element) => element.userId === this.get('model.user.object.id'));

      this.set('review_score', myReview.rating);
      this.set('review_text', myReview.review);
      this.send('ratingChanged', this.get('review_score'));
    },

    submitReviewAction() {
      this.get('ajax').post('/postReview', {
        xhrFields: {
          withCredentials: true,
        },
        data: {
          restaurantId: this.get('model.restaurant.id'),
          reviewScore: this.get('review_score'),
          reviewText: this.get('review_text'),
        },
      })
      .then(
        () => {
          $('#submitRatingModal').modal('hide');
          this.get('ajax').request('/getRestaurant/' + this.get('model.restaurant.id')).then((response) => this.set('model.restaurant', response));
        }, (error) => {
          this.set('model.hasError', true);
          this.set('model.errorMessage', error);
        }
      );
    },

    findTable() {
      this.get('ajax').post('/postReservationInquiry', {
        data: {
          restaurantId: this.get('model.restaurant.id'),
          numberOfPeople: this.get('numberOfPeople'),
          date: this.get('date'),
          time: this.get('time'),
        },
      })
      .then(
        (response) => {
          this.set('model.didFindTable', true);
          this.set('model.response', response);
        }, (error) => alert(error)
      );
    },

    reserve(time) {
      this.get('ajax').post('/postReservation', {
        contentType: 'application/json',
        data: JSON.stringify({
          restaurantId: this.get('model.response.inquiry.restaurantId'),
          numberOfPeople: this.get('model.response.inquiry.numberOfPeople'),
          date: this.get('model.response.inquiry.date'),
          time: time,
        }),
      })
      .then((response) => this.transitionToRoute('reservation-details', response.id));
    },

    setNumberOfPeople() {
      let selectBox = document.getElementById('numberOfPeople');
      this.set('numberOfPeople', selectBox.options[selectBox.selectedIndex].value);
    },

    ratingChanged(value) {
      $('#rating-control input').each((index, element) => $(element).parent().css('color', element.value < value ? '#fd6f60' : 'rgba(19, 29, 36, 0.2)'));
    },

  },

});
