package com.pl.Premier_League.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MainLayout extends AppLayout {
    public MainLayout() {

        var toggle = new DrawerToggle();
        var title = new H1("PL Fantasy");
        title.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.MEDIUM);
        addToNavbar(toggle, title, new Span("")); // space

        SideNav nav = new SideNav();
        nav.addItem(new SideNavItem("Players", "players", new Icon(VaadinIcon.USERS)));
        nav.addItem(new SideNavItem("Fantasy", "fantasy", new Icon(VaadinIcon.MONEY)));
        addToDrawer(new Scroller(nav));
    }
}
