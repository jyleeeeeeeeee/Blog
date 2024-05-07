package jylee.blog.app.repository;

import jylee.blog.app.entity.Portfolio;
import jylee.blog.app.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}
