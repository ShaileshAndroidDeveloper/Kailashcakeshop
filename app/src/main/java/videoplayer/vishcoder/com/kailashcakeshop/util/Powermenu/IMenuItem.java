package videoplayer.vishcoder.com.kailashcakeshop.util.Powermenu;

import android.widget.ListView;
import java.util.List;

/**
 * IMenuItem is an interface of the {@link MenuBaseAdapter} collections.
 *
 * @param <T> item type.
 */
public interface IMenuItem<T> {
  /**
   * add an item.
   *
   * @param item item.
   */
  void addItem(T item);

  /**
   * add an item on a specific position.
   *
   * @param position position of the list.
   * @param item an item.
   */
  void addItem(int position, T item);

  /**
   * add an item list.
   *
   * @param itemList an item list.
   */
  void addItemList(List<T> itemList);

  /**
   * gets the {@link ListView}.
   *
   * @return {@link ListView}.
   */
  ListView getListView();

  /**
   * sets the {@link ListView}.
   *
   * @param listView {@link ListView}.
   */
  void setListView(ListView listView);

  /**
   * gets the selected item position.
   *
   * @return the selected item position.
   */
  int getSelectedPosition();

  /**
   * sets the selected item position.
   *
   * @param position a selected item position.
   */
  void setSelectedPosition(int position);

  /**
   * removes an item of the list.
   *
   * @param item an item.
   */
  void removeItem(T item);

  /**
   * removes an item on a specific position.
   *
   * @param position an item position.
   */
  void removeItem(int position);

  /** clears all the items of the list. */
  void clearItems();

  /**
   * gets the item list.
   *
   * @return {@link List}.
   */
  List<T> getItemList();

  /**
   * gets the content view height of the list.
   *
   * @return the content view height of the list.
   */
  int getContentViewHeight();
}