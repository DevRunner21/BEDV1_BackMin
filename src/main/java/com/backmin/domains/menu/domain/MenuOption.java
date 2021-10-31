package com.backmin.domains.menu.domain;

import com.backmin.domains.common.BaseEntity;
import com.backmin.domains.order.domain.OrderMenuOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "menu_option")
public class MenuOption extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "menu_option_id", nullable = false)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    private Long topOptionId;

    private int maxOptionQuantity;

    private int minOptionQuantity;

    private boolean isSoldOut;

    @OneToMany(mappedBy = "menuOption", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderMenuOption> orderMenuOptions = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Builder
    public MenuOption(Long id,
            String name,
            Long topOptionId,
            int maxOptionQuantity,
            int minOptionQuantity,
            boolean isSoldOut,
            Menu menu
    ) {
        this.id = id;
        this.name = name;
        this.topOptionId = topOptionId;
        this.maxOptionQuantity = maxOptionQuantity;
        this.minOptionQuantity = minOptionQuantity;
        this.isSoldOut = isSoldOut;
        this.menu = menu;
        this.orderMenuOptions = new ArrayList<>();
    }

    public void addOrderMenuOption(OrderMenuOption orderMenuOption) {
        orderMenuOption.changeMenuOption(this);
    }

    public void changeMenu(Menu menu) {
        if (Objects.nonNull(this.menu)) {
            this.menu.getMenuOptions().remove(this);
        }

        this.menu = menu;
        menu.getMenuOptions().add(this);
    }

}
