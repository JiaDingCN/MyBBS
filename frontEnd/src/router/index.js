import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);
const routes = [
  {
    path: "/",
    name: "home",
    component: () => import("../components/mainPage/home")
  },
  {
    path: "/post/:page_id",
    name: "tiezi",
    meta: { requireAuth: true },
    component: () => import("../components/post/page")
  },
  {
    path: "/admin",
    name: "admin",
    meta: { requireAuth: true },
    component: () => import("../components/admin/pinel"),
    children: [
      {
        path: "dashboard",
        name: "dashboard",
        component: () => import("../components/admin/dashboard")
      }
    ]
  },
  {
    path: "/admin",
    redirect: "/admin/dashboard"
  },
  {
    path: "/block/:block_id",
    meta: { requireAuth: true },
    redirect: "/block/:block_id/1"
  },
  {
    path: "/block/:block_id/:count",
    name: "block",
    meta: { requireAuth: true },
    component: () => import("../components/block/showpages")
  },

  {
    path: "/userHome/:userId",
    meta: { requireAuth: true },
    name: "userhome",
    component: () => import("../components/userHome/userhome"),
    children: [
      {
        path: "profile",
        name: "profile",
        component: () => import("../components/userHome/profile")
      },
      {
        path: "posts",
        redirect: "posts/1"
      },
      {
        path: "posts/:count",
        name: "posts",
        component: () => import("../components/userHome/posts")
      },
      {
        path: "following",
        name: "following",
        component: () => import("../components/userHome/following")
      },
      {
        path: "collection",
        redirect: "collection/1"
      },
      {
        path: "collection/:count",
        name: "collection",
        component: () => import("../components/userHome/collection")
      },
      {
        path: "",
        redirect: "profile"
      }
    ]
  },

  {
    path: "/login",
    name: "login",
    component: () => import("../components/login/login")
  },
  {
    path: "/signin",
    name: "signin",
    component: () => import("../components/login/signin")
  },
  {
    path: "/aboutus",
    name: "aboutus",
    component: () => import("../components/about/about")
  },
  {
    path: "/search/:block/:keyword",
    redirect: "/search/:block/:keyword/1"
  },
  {
    path: "/search/:block/:keyword/:count",
    name: "search",
    meta: { requireAuth: true },
    component: () => import("../components/search/search")
  },
  {
    path: "*",
    name: "404",
    component: () => import("../components/notfound/notFound")
  }
];

const router = new VueRouter({
  mode: "hash",
  routes
});

export default router;
