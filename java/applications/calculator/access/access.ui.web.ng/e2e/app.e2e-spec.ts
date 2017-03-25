import { App.Calculator.Access.Ui.Web.NgPage } from './app.po';

describe('app.calculator.access.ui.web.ng App', () => {
  let page: App.Calculator.Access.Ui.Web.NgPage;

  beforeEach(() => {
    page = new App.Calculator.Access.Ui.Web.NgPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
