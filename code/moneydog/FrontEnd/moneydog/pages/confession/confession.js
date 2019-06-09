// pages/index3/index3.js
Page({
  /**
   * 初始数据
   */
  data: {
  	confessionList: [
  		{"detail":"初二一班的李子明同学，你妈妈拿了两罐旺仔牛奶给你。"},
  		{"detail":"哇，你妈妈好爱你呀！"},
  		{"detail":"初二一班的李子明同学，你妈妈拿了两罐旺仔牛奶给你。"},
  		{"detail":"哇，你妈妈好爱你呀！"},
  		{"detail":"初二一班的李子明同学，你妈妈拿了两罐旺仔牛奶给你。"},
  		{"detail":"哇，你妈妈好爱你呀！"}
  	]
  },
  writeConfession: function () {
  	wx.navigateTo({
      url: '../confession/confession_expression'
    })
  },
  onLoad: function() {
    var that = this
    wx.request({
      url: 'https://moneydog.club:3336/LoveWall/getLoveWall',
      success: function(res) {
        console.log(res.data.data)
        that.setData({confessionList: res.data.data})
      }
    })
  }
})

