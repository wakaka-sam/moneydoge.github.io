// pages/confession/confession_expression.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    content:"",
    sessionID: "847694c4-14dd-47b2-8922-facd8e379f47",/*注意与用户有关的交互都需要sessionID,初始化为永久sessionId*/
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    const session_id = wx.getStorageSync('SessionId');
    if (session_id != null) {
      that.setData({ sessionID: session_id })
    }
    that.setData({
      content:""
    })

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  //获取表白墙发布的内容
  confession_content: function (e) {
    this.setData({
      content: e.detail.value
    })
  },
  gotoupload5:function(){
    var that = this;
    console.log(that.data.content);
    var content_t = that.data.content;
    content_t = content_t.replace(/\s*/g, "");///清除content_t中的空格
    console.log(content_t.toString.length);
    if (content_t) {//不符合要求的内容不能上传
      wx.request({
        url: "https://moneydog.club:3336/LoveWall/createWall",
        header: { sessionId: that.data.sessionID.toString(), "Content-Type": "application/x-www-form-urlencoded" },//请求时要加上sessionID
        method: "POST",
        data: {
          detail: that.data.content,
        },
        success: function (res) {
          //console.log("表白:" + that.data.content);
          //先试一试内容与空格比较
          //空格的数量是个问题
          //试试清除输入的空格
          //哈哈哈哈哈好了
          //应该放前面，不符合不能上传
          wx.showToast({
            title: '表白发布成功',
            icon: 'success',
            duration: 2000,
            success: function () {
              setTimeout(function () {
                //要延时执行的代码
                wx.switchTab({
                  url: '../confession/confession'
                })
              }, 2000) //延迟时间
            }
          })
        },
      })
    }else{
      wx.showToast({
        title: '不能发布空内容',
        icon: 'none',
        duration: 2000,
      })
    }
  }
})