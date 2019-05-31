// pages/index3/index3.js
Page({
  /**
   * 初始数据
   */
  data: {
  	confessionList: [
  		{"content":"初二一班的李子明同学，你妈妈拿了两罐旺仔牛奶给你。"},
  		{"content":"哇，你妈妈好爱你呀！"},
  		{"content":"初二一班的李子明同学，你妈妈拿了两罐旺仔牛奶给你。"},
  		{"content":"哇，你妈妈好爱你呀！"},
  		{"content":"初二一班的李子明同学，你妈妈拿了两罐旺仔牛奶给你。"},
  		{"content":"哇，你妈妈好爱你呀！"}
  	]
  },
  writeConfession: function () {
  	wx.navigateTo({
      url: ''
    })
  }
})
