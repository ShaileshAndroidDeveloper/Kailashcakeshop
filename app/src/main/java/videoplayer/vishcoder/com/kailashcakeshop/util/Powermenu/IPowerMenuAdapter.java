package videoplayer.vishcoder.com.kailashcakeshop.util.Powermenu;


interface IPowerMenuAdapter {
  /**
   * sets the color of the default item text.
   *
   * @param color color value.
   */
  void setTextColor(int color);

  /**
   * sets the color of the menu item color.
   *
   * @param color color value.
   */
  void setMenuColor(int color);

  /**
   * sets the color of the selected item text.
   *
   * @param color color value.
   */
  void setSelectedTextColor(int color);

  /**
   * sets the color of the selected menu item color.
   *
   * @param color color value.
   */
  void setSelectedMenuColor(int color);

  /**
   * sets the selected effects what changing colors of the selected menu item.
   *
   * @param selectedEffect enable or unable.
   */
  void setSelectedEffect(boolean selectedEffect);
}