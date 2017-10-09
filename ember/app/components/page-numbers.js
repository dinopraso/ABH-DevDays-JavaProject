import Ember from 'ember';

const {
  computed,
} = Ember;


export default Ember.Component.extend({
  canGoBack: computed('currentPage', function () {
    return this.get('currentPage') > 1;
  }),

  canGoFoward: computed('currentPage', 'maxPages', function () {
    return  this.get('maxPages') > this.get('currentPage');
  }),

  previousPage: computed('currentPage', function () {
    return this.get('currentPage') - 1;
  }),

  nextPage: computed('currentPage', function () {
    return this.get('currentPage') + 1;
  }),

  previousPages: computed('currentPage', function () {
    let currentPage = this.get('currentPage');
    switch (currentPage) {
      case 2:
        return [{ isDots: false, pageNumber: 1 }];
      case 3:
        return [{ isDots: false, pageNumber: 1 }, { isDots: false, pageNumber: 2 }];
      case 4:
        return [{ isDots: false, pageNumber: 1 }, { isDots: false, pageNumber: 2 }, { isDots: false, pageNumber: 3 }];
      case 5:
        return [{ isDots: false, pageNumber: 1 }, { isDots: false, pageNumber: 2 }, { isDots: false, pageNumber: 3 }, { isDots: false, pageNumber: 4 }];
      default:
        return [{ isDots: false, pageNumber: 1 }, { isDots: true }, { isDots: false, pageNumber: currentPage - 3 }, { isDots: false, pageNumber: currentPage - 2 }, { isDots: false, pageNumber: currentPage - 1 }];
    }
  }),

  nextPages: computed('currentPage', 'maxPages', function () {
    let currentPage = this.get('currentPage');
    let lastPage = this.get('maxPages');
    switch (lastPage - currentPage) {
      case 1:
        return [{ isDots: false, pageNumber: lastPage }];
      case 2:
        return [{ isDots: false, pageNumber: lastPage - 1 }, { isDots: false, pageNumber: lastPage }];
      case 3:
        return [{ isDots: false, pageNumber: lastPage - 2 }, { isDots: false, pageNumber: lastPage - 1 }, { isDots: false, pageNumber: lastPage }];
      case 4:
        return [{ isDots: false, pageNumber: lastPage - 3 }, { isDots: false, pageNumber: lastPage - 2 }, { isDots: false, pageNumber: lastPage - 1 }, { isDots: false, pageNumber: lastPage }];
      default:
        return [{ isDots: false, pageNumber: currentPage + 1 }, { isDots: false, pageNumber: currentPage + 2 }, { isDots: false, pageNumber: currentPage + 3 }, { isDots: true }, { isDots: false, pageNumber: lastPage }];
    }
  }),
});
