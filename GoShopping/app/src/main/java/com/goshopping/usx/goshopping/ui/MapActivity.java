package com.goshopping.usx.goshopping.ui;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mapapi.search.route.BikingRouteLine;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.goshopping.usx.goshopping.R;
import com.goshopping.usx.goshopping.bean.MerchantBean;
import com.goshopping.usx.goshopping.ui.fragment.itemMapBottomFragment;
import com.goshopping.usx.goshopping.utils.BitmapUtil;
import com.goshopping.usx.goshopping.utils.MapUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hwj on 2016-10-17.
 */

public class MapActivity extends AppCompatActivity
        implements View.OnClickListener, BaiduMap.OnMarkerClickListener, ViewPager.OnPageChangeListener {

    private static final String TAG = "MapActivity";

    private ViewPager mViewPager;
    private MerchantBean merchant;

    private TextureMapView mMapView = null;
    private BaiduMap mBaiduMap = null;
    private TextView nextStep;
    private TextView preStep;

    //定位组件
    private LocationClient mLocationClient = null;
    private BDLocationListener myListener;
    private BDLocation mLocation;
    private int mCurrentLevel = 15;//当前缩放等级
    private PoiSearch mPoiSearch;
    private MapUtils mapUtils;

    //底部viewPage的fragment集合
    private List<itemMapBottomFragment> bottomFragments = new ArrayList<itemMapBottomFragment>();

    //起始坐标
    private LatLng startPoin = null;
    //终点坐标
    private LatLng endPoin = null;

    //是否显示单个商家
    private boolean isSingleMerchant = false;

    /**
     * 路线方式
     */
    private final static int WAY_WALK = 0;
    private final static int WAY_BUS = 1;
    private final static int WAY_CAR = 2;
    private final static int WAY_BIKE = 3;
    private int WAY = -1;

    //线路节点导航
    private int nodeIndex = -1; // 节点索引,供浏览节点时使用

    //商家集合
    private List<MerchantBean> merchantList = new ArrayList<MerchantBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setupActionBar();
        setTitle("附近的商家");
        Log.e(TAG, "onCreate: " + merchantList.size());


        if (merchantList.size() == 0) {

            merchant = (MerchantBean) getIntent().getSerializableExtra("merchant");
//        way = getIntent().getIntExtra("way", 0);
//        way = WAY_WALK;
            merchant = new MerchantBean();
            merchant.setName("越秀外国语学院")
                    .setCity("绍兴")
                    .setIntroduce("是一所二本院校")
                    .setLatitude(29.97909809718922)
                    .setLongitude(120.60366807053641);
            merchantList.add(merchant);

            merchant = new MerchantBean();
            merchant.setName("绍兴文理学院南山校区")
                    .setCity("绍兴")
                    .setIntroduce("是一所二本院校")
                    .setLatitude(29.98795886718822)
                    .setLongitude(120.5777699226923);
            merchantList.add(merchant);

            merchant = new MerchantBean();
            merchant.setName("天马大厦")
                    .setCity("绍兴")
                    .setIntroduce("是一所二本院校")
                    .setLatitude(29.986989148405655)
                    .setLongitude(120.58723806276434);
            merchantList.add(merchant);

            merchant = new MerchantBean();
            merchant.setName("风和苑")
                    .setCity("绍兴")
                    .setIntroduce("是一所二本院校")
                    .setLatitude(29.990797585721705)
                    .setLongitude(120.57980907619927);
            merchantList.add(merchant);

//        if (merchant != null) {
//            isSingleMerchant = true;
//        }

        }
        initView();
        initData();
        initEven();

    }

    private void initView() {

        mViewPager = (ViewPager) findViewById(R.id.viewPager_map);

        //获取地图控件引用
        mMapView = (TextureMapView) findViewById(R.id.mapView_map);
        mBaiduMap = mMapView.getMap();

        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //poi搜索
        mPoiSearch = PoiSearch.newInstance();

        nextStep = (TextView) findViewById(R.id.next_map);
        preStep = (TextView) findViewById(R.id.pre_map);

        nextStep.setVisibility(View.GONE);
        preStep.setVisibility(View.GONE);

        addBottomFragments();
    }

    private void initData() {
        //定位核心类
        mLocationClient = new LocationClient(getApplicationContext());//声明LocationClient类

        initLocation();//初始化
        mapUtils = MapUtils.getInstance();
    }

    private void initEven() {
        nextStep.setOnClickListener(this);
        preStep.setOnClickListener(this);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setCurrentItem(1);

        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
//                mBaiduMap.clear();//清空百度地图覆盖物
                //获取POI检索结果
                List<PoiInfo> allAddr = poiResult.getAllPoi();
                if (allAddr == null) {
                    Toast.makeText(MapActivity.this, "附近没有搜索到您需要搜索的商家", Toast.LENGTH_LONG).show();
                    return;
                }
                if (allAddr.size() == 1) {
                    Toast.makeText(MapActivity.this, "已定位到该商家", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MapActivity.this, "为你找到" + allAddr.size() + "家商家", Toast.LENGTH_LONG).show();
                }

                for (PoiInfo p : allAddr) {
                    //定义Maker坐标点
                    LatLng point = new LatLng(p.location.latitude, p.location.longitude);

                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(
                            BitmapUtil.BitmapChangeColor(BitmapFactory.decodeResource(getResources(), R.drawable.ic_map_marker_grey600_24dp), Color.RED));

                    //构建MarkerOption，用于在地图上添加Marker
                    OverlayOptions option = new MarkerOptions()
                            .position(point)
                            .icon(bitmapDescriptor);

                    //在地图上添加Marker，并显示
                    mBaiduMap.addOverlay(option);

                    if (isSingleMerchant) {
                        endPoin = point;
                        switch (WAY) {
                            case WAY_BIKE:
                                mapUtils.PaintBikingRoute(MapActivity.this, mBaiduMap, startPoin, endPoin);
                                break;
                            case WAY_CAR:
                                mapUtils.PaintDrivingRoute(MapActivity.this, mBaiduMap, startPoin, endPoin);
                                break;
                            case WAY_WALK:
                                mapUtils.PaintWalkingRoute(MapActivity.this, mBaiduMap, startPoin, endPoin);
                                break;
                            case WAY_BUS:
                                mapUtils.PaintTransitRoute(MapActivity.this, mBaiduMap, startPoin, endPoin);
                                break;
                        }
                        //设置导航按钮可见
                        nextStep.setVisibility(View.VISIBLE);
                        preStep.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        });

        mBaiduMap.setOnMarkerClickListener(this);
        myListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myListener);//注册监听函数
        mLocationClient.start();
    }

    private void search(String query, int count) {
        if (mLocation == null) {
            if (count == 1) {
                Toast.makeText(MapActivity.this, "正在定位到商家地址..", Toast.LENGTH_LONG).show();
            }
            Toast.makeText(MapActivity.this, "正在尝试为你定位..", Toast.LENGTH_LONG).show();
        }
        mPoiSearch.searchNearby((new PoiNearbySearchOption())
                .location(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()))
                .keyword(query)
                .radius(1000)
                .pageCapacity(count)
                .sortType(PoiSortType.distance_from_near_to_far));
    }

    private void startLocationOverlap() {
        //开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

        //构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(mLocation.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(0).latitude(mLocation.getLatitude())
                .longitude(mLocation.getLongitude()).build();

        // 设置定位数据
        mBaiduMap.setMyLocationData(locData);

        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, null);
        mBaiduMap.setMyLocationConfigeration(config);

        //中心点为我的位置
        LatLng cenpt = new LatLng(locData.latitude, locData.longitude);

        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(mCurrentLevel)
                .build();

        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);

        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);

        // 当不需要定位图层时关闭定位图层
        //mBaiduMap.setMyLocationEnabled(false);
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 2000;
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        RouteLine route = mapUtils.getNowRoute();

        if (route == null || route.getAllStep() == null) {
            mapUtils.PaintWalkingRoute(MapActivity.this, mBaiduMap, startPoin, endPoin);
            Toast.makeText(MapActivity.this, "自动为您生成步行规划", Toast.LENGTH_LONG).show();
            return;
        }
        if (nodeIndex == -1 && v.getId() == R.id.pre_map) {
            return;
        }

        // 设置节点索引
        if (id == R.id.next_map) {
            if (nodeIndex < route.getAllStep().size() - 1) {
                nodeIndex++;
            } else {
                return;
            }
        } else if (id == R.id.pre_map) {
            if (nodeIndex > 0) {
                nodeIndex--;
            } else {
                return;
            }
        }

        //设置文字导航
        setStep();
    }

    private void setStep() {
        LatLng nodeLocation = null;
        String nodeTitle = null;
        Object step = null;
        RouteLine route = mapUtils.getNowRoute();
        if (route == null) {
            return;
        }

        // 获取节结果信息
        step = route.getAllStep().get(nodeIndex);
        if (step instanceof DrivingRouteLine.DrivingStep) {
            nodeLocation = ((DrivingRouteLine.DrivingStep) step).getEntrance().getLocation();
            nodeTitle = ((DrivingRouteLine.DrivingStep) step).getInstructions();
        } else if (step instanceof WalkingRouteLine.WalkingStep) {
            nodeLocation = ((WalkingRouteLine.WalkingStep) step).getEntrance().getLocation();
            nodeTitle = ((WalkingRouteLine.WalkingStep) step).getInstructions();
        } else if (step instanceof TransitRouteLine.TransitStep) {
            nodeLocation = ((TransitRouteLine.TransitStep) step).getEntrance().getLocation();
            nodeTitle = ((TransitRouteLine.TransitStep) step).getInstructions();
        } else if (step instanceof BikingRouteLine.BikingStep) {
            nodeLocation = ((BikingRouteLine.BikingStep) step).getEntrance().getLocation();
            nodeTitle = ((BikingRouteLine.BikingStep) step).getInstructions();
        }

        if (nodeLocation == null || nodeTitle == null) {
            return;
        }

        // 移动节点至中心
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(nodeLocation));
        // show popup
        TextView popupText = new TextView(MapActivity.this);
        popupText.setBackgroundResource(R.drawable.circle_write_bg);
        popupText.setSingleLine(false);
        popupText.setMaxWidth(500);
        popupText.setTextColor(0xFF000000);
        popupText.setText(nodeTitle);
        popupText.setTextSize(20);
        mBaiduMap.showInfoWindow(new InfoWindow(popupText, nodeLocation, 0));
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int length = merchantList.size();
        if (position > length) {
            mViewPager.setCurrentItem(1, false);
        } else if (position == 0 && positionOffset == 0) {
            mViewPager.setCurrentItem(length, false);
        }

    }

    @Override
    public void onPageSelected(int position) {
        position--;
        int length = merchantList.size();
        if (position == length) {
            position = 0;

        } else if (position == -1) {
            position = length - 1;
        }

        updateMapMaker(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        LatLng position = marker.getPosition();
        int index = 0;
        for (MerchantBean merchant : merchantList) {
            if (merchant.getLatitude() == position.latitude && merchant.getLongitude() == position.longitude) {
                break;
            }
            index++;
        }
        mViewPager.setCurrentItem(index + 1);
        updateMapMaker(index);
        return true;
    }

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }

            mLocation = location;

            updateMapMaker(0);

            startPoin = new LatLng(location.getLatitude(), location.getLongitude());
            Log.e("tag", "onReceiveLocation: " + startPoin.latitude + " 我的位置" + startPoin.longitude);
            startLocationOverlap();//开启我的位置图层

            LatLng nodeLocation = new LatLng(merchantList.get(0).getLatitude(), merchantList.get(0).getLongitude());
            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(nodeLocation));

        }

    }

    private void updateMapMaker(int selectPosition) {
        mBaiduMap.clear();
        for (int i = 0; i < merchantList.size(); i++) {
            int color = Color.RED;
            if (i == selectPosition) {
                color = Color.parseColor("#436EEE");
            }
            MerchantBean merchant = merchantList.get(i);
            addMerchantMaker(merchant.getLatitude(), merchant.getLongitude(), color);
        }

        LatLng location = new LatLng(merchantList.get(selectPosition).getLatitude(), merchantList.get(selectPosition).getLongitude());
        MapStatusUpdate Status = MapStatusUpdateFactory.newLatLng(location);
        mBaiduMap.animateMapStatus(Status);
    }

    private void addMerchantMaker(double latitude, double longitude, int color) {
        //定义Maker坐标点
        LatLng point = new LatLng(latitude, longitude);

        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(
                BitmapUtil.BitmapChangeColor(BitmapFactory.decodeResource(getResources(), R.drawable.ic_map_marker_grey600_24dp), color));

        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmapDescriptor);

        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);


        if (isSingleMerchant) {
            endPoin = point;
            switch (WAY) {
                case WAY_BIKE:
                    mapUtils.PaintBikingRoute(MapActivity.this, mBaiduMap, startPoin, endPoin);
                    break;
                case WAY_CAR:
                    mapUtils.PaintDrivingRoute(MapActivity.this, mBaiduMap, startPoin, endPoin);
                    break;
                case WAY_WALK:
                    mapUtils.PaintWalkingRoute(MapActivity.this, mBaiduMap, startPoin, endPoin);
                    break;
                case WAY_BUS:
                    mapUtils.PaintTransitRoute(MapActivity.this, mBaiduMap, startPoin, endPoin);
                    break;
            }
            //设置导航按钮可见
            nextStep.setVisibility(View.VISIBLE);
            preStep.setVisibility(View.VISIBLE);
        }
    }

    public void addBottomFragments() {

        Bundle args = new Bundle();
        args.putString("title", merchantList.get(merchantList.size() - 1).getName());
        args.putString("summary", merchantList.get(merchantList.size() - 1).getIntroduce());
        itemMapBottomFragment bottomFragment = itemMapBottomFragment.newInstance(args);

        bottomFragments.add(bottomFragment);

        for (MerchantBean merchant : merchantList) {
            args = new Bundle();
            args.putString("title", merchant.getName());
            args.putString("summary", merchant.getIntroduce());
            bottomFragment = itemMapBottomFragment.newInstance(args);

            bottomFragments.add(bottomFragment);
        }

        args = new Bundle();
        args.putString("title", merchantList.get(0).getName());
        args.putString("summary", merchantList.get(0).getIntroduce());
        bottomFragment = itemMapBottomFragment.newInstance(args);

        bottomFragments.add(bottomFragment);

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return bottomFragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return bottomFragments.get(position);
            }
        };
        mViewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_map_myLocation:
                mLocationClient.start();
                startLocationOverlap();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mPoiSearch.destroy();
        mMapView.onDestroy();
        // 退出时销毁定位
        mLocationClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);

        mMapView = null;
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
        super.onPause();

    }


    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.activity_in_back, R.anim.activity_out_back);
        super.onBackPressed();
    }
}
