const path = require('path');
const fs = require('fs-extra');
const webpack = require('webpack');
const config = require('./config');
const helper = require('./helper');
const glob = require('glob');
const vueLoaderConfig = require('./vue-loader.conf');
const vueWebTemp = helper.rootNode(config.templateDir);
const CopyPlugin = require('copy-webpack-plugin');
const hasPluginInstalled = fs.existsSync(helper.rootNode(config.pluginFilePath));
const isWin = /^win/.test(process.platform);
const weexEntry = {
  'index': helper.root('entry.js'),
  'coupon': helper.root('coupon.js'),
  'searchPage': helper.root('searchPage.js'),
  'findMore': helper.root('findMore.js'),
  'goodsShow': helper.root('goodsShow.js'),
  'snapUp': helper.root('snapUp.js'),
  'jdd': helper.root('jdd.js'), //拼团
  'about': helper.root('about.js'),
  'searchGoodsShowWithTab': helper.root('searchGoodsShowWithTab.js'),
  'goodsShowNoTab': helper.root('goodsShowNoTab.js'),
  'setting': helper.root('setting.js'),
}

const getEntryFileContent = (source, routerpath) => {
  let dependence = `import Vue from 'vue'\n`;
  dependence += `import weex from 'weex-vue-render'\n`;
  let relativePluginPath = helper.rootNode(config.pluginFilePath);
  let entryContents = fs.readFileSync(source).toString();
  let contents = '';
  entryContents = dependence + entryContents;
  entryContents = entryContents.replace(/\/\* weex initialized/, match => `weex.init(Vue)\n${match}`);
  if (isWin) {
    relativePluginPath = relativePluginPath.replace(/\\/g, '\\\\');
  }
  if (hasPluginInstalled) {
    contents += `\n// If detact plugins/plugin.js is exist, import and the plugin.js\n`;
    contents += `import plugins from '${relativePluginPath}';\n`;
    contents += `plugins.forEach(function (plugin) {\n\tweex.install(plugin)\n});\n\n`;
    entryContents = entryContents.replace(/\.\/router/, routerpath);
    entryContents = entryContents.replace(/weex\.init/, match => `${contents}${match}`);
  }
  return entryContents;
}

const getRouterFileContent = (source) => {
  const dependence = `import Vue from 'vue'\n`;
  let routerContents = fs.readFileSync(source).toString();
  routerContents = dependence + routerContents;
  return routerContents;
}

const getEntryFile = () => {
  const entryFile = path.join(vueWebTemp, config.entryFilePath)
  const routerFile = path.join(vueWebTemp, config.routerFilePath)

  fs.outputFileSync(entryFile, getEntryFileContent(helper.root(config.entryFilePath), routerFile));
  fs.outputFileSync(routerFile, getRouterFileContent(helper.root(config.routerFilePath)));
  
  const couponFilePath = 'coupon.js'
  const couponFile = path.join(vueWebTemp, couponFilePath)
  fs.outputFileSync(couponFile, getEntryFileContent(helper.root(couponFilePath), routerFile));

  const searchPagePath = 'searchPage.js'
  const searchPage = path.join(vueWebTemp, searchPagePath)
  fs.outputFileSync(searchPage, getEntryFileContent(helper.root(searchPagePath), routerFile));

  const findMorePath = 'findMore.js'
  const findMore = path.join(vueWebTemp, findMorePath)
  fs.outputFileSync(findMore, getEntryFileContent(helper.root(findMorePath), routerFile));

  const goodsShowPath = 'goodsShow.js'
  const goodsShow = path.join(vueWebTemp, goodsShowPath)
  fs.outputFileSync(goodsShow, getEntryFileContent(helper.root(goodsShowPath), routerFile));

  const snapUpPath = 'snapUp.js'
  const snapUp = path.join(vueWebTemp, snapUpPath)
  fs.outputFileSync(snapUp, getEntryFileContent(helper.root(snapUpPath), routerFile));

  const jddPath = 'jdd.js'
  const jdd = path.join(vueWebTemp, jddPath)
  fs.outputFileSync(jdd, getEntryFileContent(helper.root(jddPath), routerFile));

  const aboutPath = 'about.js'
  const about = path.join(vueWebTemp, aboutPath)
  fs.outputFileSync(about, getEntryFileContent(helper.root(aboutPath), routerFile));

  const searchGoodsShowWithTabPath = 'searchGoodsShowWithTab.js'
  const searchGoodsShowWithTab = path.join(vueWebTemp, searchGoodsShowWithTabPath)
  fs.outputFileSync(searchGoodsShowWithTab, getEntryFileContent(helper.root(searchGoodsShowWithTabPath), routerFile));

  const goodsShowNoTabPath = 'goodsShowNoTab.js'
  const goodsShowNoTab = path.join(vueWebTemp, goodsShowNoTabPath)
  fs.outputFileSync(goodsShowNoTab, getEntryFileContent(helper.root(goodsShowNoTabPath), routerFile));

  const settingPath = 'setting.js'
  const setting = path.join(vueWebTemp, settingPath)
  fs.outputFileSync(setting, getEntryFileContent(helper.root(settingPath), routerFile));
  return {
    index: entryFile,
    coupon: couponFile,
    searchPage: searchPage,
    goodsShow: goodsShow,
    snapUp: snapUp,
    jdd: jdd,
    about: about,
    searchGoodsShowWithTab: searchGoodsShowWithTab,
    goodsShowNoTab: goodsShowNoTab,
    setting: setting
  }
}

// The entry file for web needs to add some library. such as vue, weex-vue-render
// 1. src/entry.js 
// import Vue from 'vue';
// import weex from 'weex-vue-render';
// weex.init(Vue);
// 2. src/router/index.js
// import Vue from 'vue'
const webEntry = getEntryFile();



/**
 * Plugins for webpack configuration.
 */
const plugins = [
  /**
   * Plugin: webpack.DefinePlugin
   * Description: The DefinePlugin allows you to create global constants which can be configured at compile time. 
   *
   * See: https://webpack.js.org/plugins/define-plugin/
   */
  new webpack.DefinePlugin({
    'process.env': {
      'NODE_ENV': config.dev.env
    }
  }),
  /*
   * Plugin: BannerPlugin
   * Description: Adds a banner to the top of each generated chunk.
   * See: https://webpack.js.org/plugins/banner-plugin/
   */
  new webpack.BannerPlugin({
    banner: '// { "framework": "Vue"} \n',
    raw: true,
    exclude: 'Vue'
  }),
  
  new CopyPlugin([{
    from: path.resolve('src/public/images'),
    to: path.resolve('platforms/android/app/src/main/assets/images')
  }])
];

// Config for compile jsbundle for web.
const webConfig = {
  entry: Object.assign(webEntry, {
    'vendor': [path.resolve('node_modules/phantom-limb/index.js')]
  }),
  output: {
    path: helper.rootNode('./dist'),
    filename: '[name].web.js'
  },
  /**
   * Options affecting the resolving of modules.
   * See http://webpack.github.io/docs/configuration.html#resolve
   */
  resolve: {
    extensions: ['.js', '.vue', '.json'],
    alias: {
      '@': helper.resolve('src')
    }
  },
  /*
   * Options affecting the resolving of modules.
   *
   * See: http://webpack.github.io/docs/configuration.html#module
   */
  module: {
    // webpack 2.0 
    rules: [
      {
        test: /\.js$/,
        use: [{
          loader: 'babel-loader'
        }],
        exclude: config.excludeModuleReg
      },
      {
        test: /\.vue(\?[^?]+)?$/,
        use: [{
          loader: 'vue-loader',
          options: Object.assign(vueLoaderConfig({useVue: true, usePostCSS: false}), {
            /**
             * important! should use postTransformNode to add $processStyle for
             * inline style prefixing.
             */
            optimizeSSR: false,
            postcss: [
              // to convert weex exclusive styles.
              require('postcss-plugin-weex')(),
              require('autoprefixer')({
                browsers: ['> 0.1%', 'ios >= 8', 'not ie < 12']
              }),
              require('postcss-plugin-px2rem')({
                // base on 750px standard.
                rootValue: 75,
                // to leave 1px alone.
                minPixelValue: 1.01
              })
            ],
            compilerModules: [
              {
                postTransformNode: el => {
                  // to convert vnode for weex components.
                  require('weex-vue-precompiler')()(el)
                }
              }
            ]
            
          })
        }],
        exclude: config.excludeModuleReg
      }
    ]
  },
  /*
   * Add additional plugins to the compiler.
   *
   * See: http://webpack.github.io/docs/configuration.html#plugins
   */
  plugins: plugins
};
// Config for compile jsbundle for native.
const weexConfig = {
  entry: weexEntry,
  output: {
    path: path.join(__dirname, '../dist'),
    filename: '[name].js'
  },
  /**
   * Options affecting the resolving of modules.
   * See http://webpack.github.io/docs/configuration.html#resolve
   */
  resolve: {
    extensions: ['.js', '.vue', '.json'],
    alias: {
      '@': helper.resolve('src')
    }
  },
  /*
   * Options affecting the resolving of modules.
   *
   * See: http://webpack.github.io/docs/configuration.html#module
   */
  module: {
    rules: [
      {
        test: /\.js$/,
        use: [{
          loader: 'babel-loader'
        }],
        exclude: config.excludeModuleReg
      },
      {
        test: /\.vue(\?[^?]+)?$/,
        use: [{
          loader: 'weex-loader',
          options: vueLoaderConfig({useVue: false})
        }],
        exclude: config.excludeModuleReg
      }
    ]
  },
  /*
   * Add additional plugins to the compiler.
   *
   * See: http://webpack.github.io/docs/configuration.html#plugins
   */
  plugins: plugins,
  /*
  * Include polyfills or mocks for various node stuff
  * Description: Node configuration
  *
  * See: https://webpack.github.io/docs/configuration.html#node
  */
  node: config.nodeConfiguration
};

module.exports = [webConfig, weexConfig];
