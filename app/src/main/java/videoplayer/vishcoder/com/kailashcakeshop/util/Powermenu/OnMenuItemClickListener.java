package videoplayer.vishcoder.com.kailashcakeshop.util.Powermenu;

/** OnMenuItemClickListener is for listening to the item click of the popup menu. */
public interface OnMenuItemClickListener<T> {
  /**
   * invoked when the popup menu item would be clicked.
   *
   * @param position the position of the item.
   * @param item the clicked item.
   */
  void onItemClick(int position, T item);
}