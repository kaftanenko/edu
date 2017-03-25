import { browser, element, by } from 'protractor';

export class App.Calculator.Access.Ui.Web.NgPage {
  navigateTo() {
    return browser.get('/');
  }

  getParagraphText() {
    return element(by.css('app-root h1')).getText();
  }
}
