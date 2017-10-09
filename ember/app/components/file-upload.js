import Ember from 'ember';
import EmberUploader from 'ember-uploader';

const {
  isEmpty,
} = Ember;

export default EmberUploader.FileField.extend({
  filesDidChange(files) {
    const uploader = EmberUploader.Uploader.create({
      url: this.get('url'),
    });

    if (!isEmpty(files)) {
      // this second argument is optional and can to be sent as extra data with the upload
      uploader.upload(files[0], { restaurantId: this.get('restaurantId'), imageType: this.get('imageFor') });
    }

    uploader.on('progress', (e) => {
      this.set('progress', Math.round(e.percent) - 1);
    });

    uploader.on('didUpload', () => {
      this.set('progress', null);
      let explodedFilename = files[0].name.split('.');
      this.sendAction('onFinishedUpload', this.get('imageFor'), explodedFilename[explodedFilename.length - 1]);
    });
  },
});
