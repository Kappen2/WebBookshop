package com.example.webbookshop.Repository;

import com.example.webbookshop.Model.BookOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookOrderRepository extends JpaRepository<BookOrder, Long> {
    List<BookOrder> findByBookId(Long bookId);
    List<BookOrder> findByShoppingCartId(Long shoppingCartId);
}
