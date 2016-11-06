package com.goshopping.usx.goshopping.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.goshopping.usx.goshopping.R;
import com.goshopping.usx.goshopping.adapter.RouteLineAdapter;
import com.goshopping.usx.goshopping.baiduApiOverlayutil.BikingRouteOverlay;
import com.goshopping.usx.goshopping.baiduApiOverlayutil.DrivingRouteOverlay;
import com.goshopping.usx.goshopping.baiduApiOverlayutil.TransitRouteOverlay;
import com.goshopping.usx.goshopping.baiduApiOverlayutil.WalkingRouteOverlay;

import java.util.List;



/**
 * Created by hwj on 2016-10-19.
 */

public class MapUtils implements OnGetRoutePlanResultListener {

    private static MapUtils Instance = new MapUtils();

    //地图
    private BaiduMap mBaiduMap;
    //搜索模块
    private RoutePlanSearch mSearch = RoutePlanSearch.newInstance();
    //上下文
    private Context mContext;
    //选择的路线
    private RouteLine nowRoute = null;

    //步行路线集
    private WalkingRouteResult nowResultWalk = null;
    //骑行路线集
    private BikingRouteResult nowResultBike = null;
    //公交路线集
    private TransitRouteResult nowResulTransit = null;
    //驾车路线集
    private DrivingRouteResult nowResultDrive  = null;

    //是否已经初始化搜索模块
    private static boolean isInitSearchListener = false;


    //初始化搜索模块
    private void initSearch(){
        if(!isInitSearchListener){
            mSearch.setOnGetRoutePlanResultListener(this);
            isInitSearchListener = true;
        }
    }

    /**
     * 单例模式
     * @return 返回MapUtils实例
     */
    public static MapUtils getInstance(){
        return  Instance;
    }

    /**
     * 在地图上画步行路径
     * @param context           上下文(显示Toast)
     * @param baiduMap          地图
     * @param startCityName     起点城市名称
     * @param startPlaceName    起点名称
     * @param endCityName       终点城市名称
     * @param endPlaceName      终点名称
     */
    public void PaintWalkingRoute(Context context, BaiduMap baiduMap,
                                  String startCityName, String startPlaceName, String endCityName, String endPlaceName){
        initSearch();
        mBaiduMap = baiduMap;
        //起点
        PlanNode startNode = PlanNode.withCityNameAndPlaceName(startCityName,startPlaceName);
        //终点
        PlanNode endNode = PlanNode.withCityNameAndPlaceName(endCityName,endPlaceName);

        mContext = context;

        mSearch.walkingSearch((new WalkingRoutePlanOption())
                .from(startNode).to(endNode));
    }

    public void PaintWalkingRoute(Context context, BaiduMap baiduMap, LatLng startPoint, LatLng endPoint){

        initSearch();
        mBaiduMap = baiduMap;
        //起点
        PlanNode startNode = PlanNode.withLocation(startPoint);
        //终点
        PlanNode endNode = PlanNode.withLocation(endPoint);

        mContext = context;

        mSearch.walkingSearch((new WalkingRoutePlanOption())
                .from(startNode).to(endNode));
    }

    /**
     * 在地图上画公交行路径
     * @param context           上下文(显示Toast)
     * @param baiduMap          地图
     * @param startCityName     起点城市名称
     * @param startPlaceName    起点名称
     * @param endCityName       终点城市名称
     * @param endPlaceName      终点名称
     */
    public void PaintTransitRoute(Context context, BaiduMap baiduMap,
                                  String startCityName, String startPlaceName, String endCityName, String endPlaceName){
        initSearch();
        mBaiduMap = baiduMap;
        //起点
        PlanNode startNode = PlanNode.withCityNameAndPlaceName(startCityName,startPlaceName);
        //终点
        PlanNode endNode = PlanNode.withCityNameAndPlaceName(endCityName,endPlaceName);

        mContext = context;

        mSearch.transitSearch((new TransitRoutePlanOption())
                .from(startNode).to(endNode));
    }

    public void PaintTransitRoute(Context context, BaiduMap baiduMap, LatLng startPoint, LatLng endPoint){

        initSearch();
        mBaiduMap = baiduMap;
        //起点
        PlanNode startNode = PlanNode.withLocation(startPoint);
        //终点
        PlanNode endNode = PlanNode.withLocation(endPoint);

        mContext = context;

        mSearch.transitSearch((new TransitRoutePlanOption())
                .from(startNode).city("绍兴").to(endNode));
    }

    /**
     * 在地图上画驾车行路径
     * @param context           上下文(显示Toast)
     * @param baiduMap          地图
     * @param startCityName     起点城市名称
     * @param startPlaceName    起点名称
     * @param endCityName       终点城市名称
     * @param endPlaceName      终点名称
     */
    public void PaintDrivingRoute(Context context, BaiduMap baiduMap,
                                  String startCityName, String startPlaceName, String endCityName, String endPlaceName){
        initSearch();
        mBaiduMap = baiduMap;
        //起点
        PlanNode startNode = PlanNode.withCityNameAndPlaceName(startCityName,startPlaceName);
        //终点
        PlanNode endNode = PlanNode.withCityNameAndPlaceName(endCityName,endPlaceName);

        mContext = context;

        mSearch.walkingSearch((new WalkingRoutePlanOption())
                .from(startNode).to(endNode));
    }

    public void PaintDrivingRoute(Context context, BaiduMap baiduMap, LatLng startPoint, LatLng endPoint){

        initSearch();
        mBaiduMap = baiduMap;
        //起点
        PlanNode startNode = PlanNode.withLocation(startPoint);
        //终点
        PlanNode endNode = PlanNode.withLocation(endPoint);

        mContext = context;

        mSearch.walkingSearch((new WalkingRoutePlanOption())
                .from(startNode).to(endNode));
    }

    /**
     * 在地图上画骑车行路径
     * @param context           上下文(显示Toast)
     * @param baiduMap          地图
     * @param startCityName     起点城市名称
     * @param startPlaceName    起点名称
     * @param endCityName       终点城市名称
     * @param endPlaceName      终点名称
     */
    public void PaintBikingRoute(Context context, BaiduMap baiduMap,
                                 String startCityName, String startPlaceName, String endCityName, String endPlaceName){
        initSearch();
        mBaiduMap = baiduMap;
        //起点
        PlanNode startNode = PlanNode.withCityNameAndPlaceName(startCityName,startPlaceName);
        //终点
        PlanNode endNode = PlanNode.withCityNameAndPlaceName(endCityName,endPlaceName);

        mContext = context;

        mSearch.walkingSearch((new WalkingRoutePlanOption())
                .from(startNode).to(endNode));
    }

    public void PaintBikingRoute(Context context, BaiduMap baiduMap, LatLng startPoint, LatLng endPoint){

        initSearch();
        mBaiduMap = baiduMap;
        //起点
        PlanNode startNode = PlanNode.withLocation(startPoint);
        //终点
        PlanNode endNode = PlanNode.withLocation(endPoint);

        mContext = context;

        mSearch.walkingSearch((new WalkingRoutePlanOption())
                .from(startNode).to(endNode));
    }


    public RouteLine getNowRoute() {
        return nowRoute;
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(mContext, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
             result.getSuggestAddrInfo();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {

            //结果集大于1,dialog显示弹框供用户选择合适的路线
            if (result.getRouteLines().size() > 1 ) {
                nowResultWalk = result;

                //设置显示路线选择dialog
                MyTransitDlg myTransitDlg = new MyTransitDlg(mContext,
                        result.getRouteLines(),
                        RouteLineAdapter.Type.WALKING_ROUTE);

                myTransitDlg.setOnItemInDlgClickLinster(new OnItemInDlgClickListener() {
                    public void onItemClick(int position) {
                        //设置当前选择的路线
                        nowRoute = nowResultWalk.getRouteLines().get(position);
                        //创建一个显示步行路线的overlay
                        WalkingRouteOverlay overlay = new WalkingRouteOverlay(mBaiduMap);
                        //设置Marker的点击事件
                        mBaiduMap.setOnMarkerClickListener(overlay);
                        //设置用户选择的路线
                        overlay.setData(nowResultWalk.getRouteLines().get(position));
                        //添加到地图中
                        overlay.addToMap();
                        //设置overlay在地图缩放时显示合适的位置和大小
                        overlay.zoomToSpan();
                    }

                });
                myTransitDlg.show();

            } else if ( result.getRouteLines().size() == 1 ) {  //只有一条线路直接显示
                nowRoute = result.getRouteLines().get(0);
                WalkingRouteOverlay overlay = new WalkingRouteOverlay(mBaiduMap);
                mBaiduMap.setOnMarkerClickListener(overlay);
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();

            }
        }
    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(mContext, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {

            if (result.getRouteLines().size() > 1 ) {
                nowResulTransit = result;

                MyTransitDlg myTransitDlg = new MyTransitDlg(mContext,
                        result.getRouteLines(),
                        RouteLineAdapter.Type.TRANSIT_ROUTE);
                myTransitDlg.setOnItemInDlgClickLinster(new OnItemInDlgClickListener() {
                    public void onItemClick(int position) {
                        nowRoute = nowResulTransit.getRouteLines().get(position);
                        TransitRouteOverlay overlay = new TransitRouteOverlay(mBaiduMap);
                        mBaiduMap.setOnMarkerClickListener(overlay);

                        overlay.setData(nowResulTransit.getRouteLines().get(position));
                        overlay.addToMap();
                        overlay.zoomToSpan();
                    }

                });
                myTransitDlg.show();

            } else if ( result.getRouteLines().size() == 1 ) {
                // 直接显示
                nowRoute = result.getRouteLines().get(0);
                TransitRouteOverlay overlay = new TransitRouteOverlay(mBaiduMap);
                mBaiduMap.setOnMarkerClickListener(overlay);
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();

            }
        }
    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult result) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(mContext, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {

            if (result.getRouteLines().size() > 1 ) {
                nowResultDrive = result;

                MyTransitDlg myTransitDlg = new MyTransitDlg(mContext,
                        result.getRouteLines(),
                        RouteLineAdapter.Type.DRIVING_ROUTE);
                myTransitDlg.setOnItemInDlgClickLinster(new OnItemInDlgClickListener() {
                    public void onItemClick(int position) {
                        nowRoute = nowResultDrive.getRouteLines().get(position);
                        DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap);
                        mBaiduMap.setOnMarkerClickListener(overlay);

                        overlay.setData(nowResultDrive.getRouteLines().get(position));
                        overlay.addToMap();
                        overlay.zoomToSpan();
                    }

                });
                myTransitDlg.show();

            } else if ( result.getRouteLines().size() == 1 ) {
                nowRoute = result.getRouteLines().get(0);
                DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap);

                mBaiduMap.setOnMarkerClickListener(overlay);
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
            }

        }
    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(mContext, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {

            if (result.getRouteLines().size() > 1 ) {
                nowResultBike = result;

                MyTransitDlg myTransitDlg = new MyTransitDlg(mContext,
                        result.getRouteLines(),
                        RouteLineAdapter.Type.DRIVING_ROUTE);
                myTransitDlg.setOnItemInDlgClickLinster(new OnItemInDlgClickListener() {
                    public void onItemClick(int position) {
                        nowRoute = nowResultBike.getRouteLines().get(position);
                        BikingRouteOverlay overlay = new BikingRouteOverlay(mBaiduMap);
                        mBaiduMap.setOnMarkerClickListener(overlay);
                        overlay.setData(nowResultBike.getRouteLines().get(position));
                        overlay.addToMap();
                        overlay.zoomToSpan();
                    }

                });
                myTransitDlg.show();

            } else if ( result.getRouteLines().size() == 1 ) {
                nowRoute = result.getRouteLines().get(0);
                BikingRouteOverlay overlay = new BikingRouteOverlay(mBaiduMap);
                mBaiduMap.setOnMarkerClickListener(overlay);
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
            }

        }
    }


    // 响应DLg中的List item 点击
    interface OnItemInDlgClickListener {
        void onItemClick(int position);
    }

    // 供路线选择的Dialog
    private class MyTransitDlg extends Dialog {

        private List<? extends RouteLine> mtransitRouteLines;
        private ListView transitRouteList;
        private RouteLineAdapter mTransitAdapter;

        OnItemInDlgClickListener onItemInDlgClickListener;

        MyTransitDlg(Context context, int theme) {
            super(context, theme);
        }

        MyTransitDlg(Context context, List<? extends RouteLine> transitRouteLines, RouteLineAdapter.Type type) {
            this( context, 0);
            mtransitRouteLines = transitRouteLines;
            mTransitAdapter = new RouteLineAdapter( context, mtransitRouteLines , type);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_transit_dialog);

            transitRouteList = (ListView) findViewById(R.id.transitList);
            transitRouteList.setAdapter(mTransitAdapter);

            transitRouteList.setOnItemClickListener( new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onItemInDlgClickListener.onItemClick( position);
                    dismiss();
                }
            });
        }

        void setOnItemInDlgClickLinster(OnItemInDlgClickListener itemListener) {
            onItemInDlgClickListener = itemListener;

        }


    }

}
