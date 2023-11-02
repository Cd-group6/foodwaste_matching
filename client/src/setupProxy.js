const { createProxyMiddleware } = require("http-proxy-middleware");

module.exports = (app) => {
  app.use(
    "ws",
    createProxyMiddleware({
      target: "http://10.0.2.2:8080",
      ws: true,
    })
  );
};
